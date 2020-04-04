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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

public class Debugger implements Runnable {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    private final Class<?> debugClass;
    private int[] breakPointLines;

    public Debugger(Config config) {
        this.debugClass = config.get(Option.CLASS_NAME);
    }

    @Override
    public void run() {
        final int[] breakPointLines = {6, 7, 12};
        setBreakPointLines(breakPointLines);
        try {
            VirtualMachine vm = launchVm();
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

    public VirtualMachine launchVm() {
        final LaunchingConnector launchingConnector = getLaunchingConnector();
        final Map<String, Connector.Argument> arguments = getConnectorArguments(launchingConnector);
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

    private LaunchingConnector getLaunchingConnector() {
        VirtualMachineManager vmManager = getVirtualMachineManager();
        StringJoiner joiner = new StringJoiner("\n\t");
        for (Connector connector : vmManager.allConnectors()) {
            joiner.add(String.format("%s - %s", connector.name(), connector.description()));
        }
        log.info(String.format("All connectors:%n\t%s", joiner));
        LaunchingConnector connector = vmManager.defaultConnector();
        log.info(String.format("Connector: %s - %s.", connector.name(), connector.description()));
        return connector;
    }

    private VirtualMachineManager getVirtualMachineManager() {
        final VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
        final String version = vmManager.majorInterfaceVersion() + "." + vmManager.minorInterfaceVersion();
        log.info(String.format("VM version: %s.", version));
        return vmManager;
    }

    private Map<String, Connector.Argument> getConnectorArguments(LaunchingConnector connector) {
        final Map<String, Connector.Argument> arguments = connector.defaultArguments();
        final Connector.Argument mainArgument = arguments.get("main");
        mainArgument.setValue(debugClass.getName());
        log.info("Connector args: " + arguments);
        return arguments;
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
