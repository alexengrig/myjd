package dev.alexengrig.myjd.service;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjd.DebugRunner;
import dev.alexengrig.myjd.domain.Config;
import dev.alexengrig.myjd.domain.Option;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

public class Debugger implements Runnable {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    private Class<?> debugClass;
    private int[] breakPointLines;

    public Debugger(Config config) {
        this.debugClass = config.get(Option.CLASS_NAME);
    }

    @Override
    public void run() {
        final int[] breakPointLines = {6, 7};
        setBreakPointLines(breakPointLines);
        try {
            VirtualMachine vm = connectAndLaunchVM();
            enableClassPrepareRequest(vm);
            EventSet eventSet;
            while ((eventSet = vm.eventQueue().remove()) != null) {
                for (Event event : eventSet) {
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

    public VirtualMachine connectAndLaunchVM() throws VMStartException, IllegalConnectorArgumentsException, IOException {
        final VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
        final String version = vmManager.majorInterfaceVersion() + "." + vmManager.minorInterfaceVersion();
        log.info("VM version: " + version + ".");
        final LaunchingConnector launchingConnector = vmManager.defaultConnector();
        final String connectorInfo = launchingConnector.name() + " - " + launchingConnector.description();
        log.info("Connector: " + connectorInfo + ".");
        final Map<String, Connector.Argument> arguments = launchingConnector.defaultArguments();
        final Connector.Argument mainArgument = arguments.get("main");
        String debugClassName = debugClass.getName();
        mainArgument.setValue(debugClassName);
        log.info("Connector args: " + arguments);
        return launchingConnector.launch(arguments);
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
