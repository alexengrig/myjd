package dev.alexengrig.myjd;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Debugger {
    private static final Logger log = Logger.getLogger(Debugger.class.getSimpleName());

    private Class<ExampleDebuggee> debugClass;
    private int[] breakPointLines;

    public static void main(String[] args) {
        log.info("Debugger started.");
        Debugger debugger = new Debugger();
        Class<ExampleDebuggee> debugClass = ExampleDebuggee.class;
        debugger.setDebugClass(debugClass);
        int[] breakPointLines = {6, 7};
        debugger.setBreakPointLines(breakPointLines);
        VirtualMachine vm;
        try {
            vm = debugger.connectAndLaunchVM();
            debugger.enableClassPrepareRequest(vm);
            EventSet eventSet;
            while ((eventSet = vm.eventQueue().remove()) != null) {
                for (Event event : eventSet) {
                    if (event instanceof ClassPrepareEvent) {
                        debugger.createBreakpoints(vm, (ClassPrepareEvent) event);
                    }
                    if (event instanceof BreakpointEvent) {
                        debugger.displayVariables((BreakpointEvent) event);
                    }
                    vm.resume();
                }
            }
        } catch (VMDisconnectedException e) {
            log.info("Virtual Machine is disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Debugger finished.");
    }

    public void setDebugClass(Class<ExampleDebuggee> debugClass) {
        this.debugClass = debugClass;
    }

    public void setBreakPointLines(int[] breakPointLines) {
        this.breakPointLines = breakPointLines;
    }

    private VirtualMachine connectAndLaunchVM() throws VMStartException, IllegalConnectorArgumentsException, IOException {
        VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
        LaunchingConnector launchingConnector = vmManager.defaultConnector();
        Map<String, Connector.Argument> arguments = launchingConnector.defaultArguments();
        Connector.Argument mainArgument = arguments.get("main");
        String debugClassName = debugClass.getName();
        mainArgument.setValue(debugClassName);
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
        StackFrame stackFrame = event.thread().frame(0);
        Location stackLocation = stackFrame.location();
        String stackLocationName = stackLocation.toString();
        String debugClassName = debugClass.getName();
        if (stackLocationName.contains(debugClassName)) {
            List<LocalVariable> visibleVariables = stackFrame.visibleVariables();
            Map<LocalVariable, Value> variableValues = stackFrame.getValues(visibleVariables);
            log.info("Variables at " + stackLocationName + " > ");
            for (Map.Entry<LocalVariable, Value> entry : variableValues.entrySet()) {
                LocalVariable variable = entry.getKey();
                String variableName = variable.name();
                Value value = entry.getValue();
                log.info(variableName + " = " + value);
            }
        }
    }
}
