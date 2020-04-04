package dev.alexengrig.myjdi.service;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjdi.DebugRunner;
import dev.alexengrig.myjdi.domain.Config;
import dev.alexengrig.myjdi.domain.Option;
import dev.alexengrig.myjdi.util.PrettyUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

public class Debugger implements Runnable {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    private final LaunchingConnectorService launchingConnectorService;
    private final Class<?> debugClass;
    private int[] breakPointLines;

    public Debugger(Config config) {
        launchingConnectorService = new LaunchingConnectorService();
        debugClass = config.get(Option.CLASS_NAME);
    }

    @Override
    public void run() {
        final int[] breakPointLines = {6, 7, 12};
        setBreakPointLines(breakPointLines);
        try {
            VirtualMachine vm = getLaunchedVm();
            vm.setDebugTraceMode(VirtualMachine.TRACE_NONE);
            enableClassPrepareRequest(vm);
            EventSet eventSet;
            while ((eventSet = vm.eventQueue().remove()) != null) {
                for (Event event : eventSet) {
                    log.info(String.format("Event: %s.", event));
                    if (event instanceof ClassPrepareEvent) {
                        createBreakpoints(vm, (ClassPrepareEvent) event);
                    }
                    if (event instanceof BreakpointEvent) {
                        displayVariables((BreakpointEvent) event);
                    }
                    vm.resume();
                }
            }
        } catch (VMDisconnectedException e) {
            log.info("Virtual Machine is disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBreakPointLines(int[] breakPointLines) {
        this.breakPointLines = breakPointLines;
    }

    public VirtualMachine getLaunchedVm() {
        final LaunchingConnector launchingConnector = launchingConnectorService.connect();
        log.info(String.format("Connector:%n%s", PrettyUtil.pretty(launchingConnector)));
        final Map<String, String> values = Collections.singletonMap("main", debugClass.getName());
        log.info(String.format("Connector values: %s.", values));
        final Map<String, Connector.Argument> arguments = launchingConnectorService.arguments(launchingConnector, values);

        try {
            return launchingConnector.launch(arguments);
        } catch (IOException e) {
            throw new Error(String.format("Unable to launch target VM: %s.", e));
        } catch (IllegalConnectorArgumentsException e) {
            throw new Error(String.format("Internal error: %s.", e));
        } catch (VMStartException e) {
            throw new Error(String.format("Target VM failed to initialize: %s.", e.getMessage()));
        }
    }


    public void enableClassPrepareRequest(VirtualMachine vm) {
        EventRequestManager eventRequestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        String debugClassName = debugClass.getName();
        classPrepareRequest.addClassFilter(debugClassName);
        classPrepareRequest.enable();
    }

    public void createBreakpoints(VirtualMachine vm, ClassPrepareEvent event) throws AbsentInformationException {
        ClassType classType = (ClassType) event.referenceType();
        for (int lineNumber : breakPointLines) {
            List<Location> locations = classType.locationsOfLine(lineNumber);
            if (!locations.isEmpty()) {
                Location location = locations.get(0);
                EventRequestManager eventRequestManager = vm.eventRequestManager();
                BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
                breakpointRequest.enable();
            }
        }
    }

    public void displayVariables(LocatableEvent event) throws IncompatibleThreadStateException, AbsentInformationException {
        final StackFrame stackFrame = event.thread().frame(0);
        final Location stackLocation = stackFrame.location();
        final String stackLocationName = stackLocation.toString();
        final String debugClassName = debugClass.getName();
        if (stackLocationName.contains(debugClassName)) {
            final List<LocalVariable> visibleVariables = stackFrame.visibleVariables();
            final Map<LocalVariable, Value> variableValues = stackFrame.getValues(visibleVariables);
            final StringJoiner joiner = new StringJoiner(System.lineSeparator())
                    .add("Variables at " + stackLocationName + " > ");
            for (Map.Entry<LocalVariable, Value> entry : variableValues.entrySet()) {
                LocalVariable variable = entry.getKey();
                String variableName = variable.name();
                Value variableValue = entry.getValue();
                joiner.add("\t" + variableName + " = " + variableValue);
            }
            log.info(joiner.toString());
        }
    }
}
