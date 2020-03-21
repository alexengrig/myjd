package dev.alexengrig.myjd.service;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.LocatableEvent;
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

public class Debugger {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    private Class<?> debugClass;
    private int[] breakPointLines;

    public Debugger(Config config) {
        this.debugClass = config.get(Option.CLASS_NAME);
    }

    public void setBreakPointLines(int[] breakPointLines) {
        this.breakPointLines = breakPointLines;
    }

    public VirtualMachine connectAndLaunchVM() throws VMStartException, IllegalConnectorArgumentsException, IOException {
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