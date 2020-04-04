package dev.alexengrig.myjdi.service;

import com.sun.jdi.Field;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.ModificationWatchpointRequest;
import dev.alexengrig.myjdi.DebugRunner;
import dev.alexengrig.myjdi.domain.Config;
import dev.alexengrig.myjdi.domain.Option;
import dev.alexengrig.myjdi.util.PrettyUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Debugger extends Thread {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    protected final LaunchingConnectorService launchingConnectorService;
    protected final Class<?> debugClass;
    protected final Integer[] breakPointLines;

    protected VirtualMachine vm;
    protected boolean connected;
    protected boolean died;

    public Debugger(Config config) {
        super("myjdi");
        launchingConnectorService = new LaunchingConnectorService();
        debugClass = config.get(Option.CLASS_NAME);
        breakPointLines = config.get(Option.BREAK_POINTS);
    }

    @Override
    public void run() {
        prepareVm();
        final EventQueue eventQueue = vm.eventQueue();
        while (connected) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    handleEvent(event);
                }
                vm.resume(); //FIXME: call it after all or after each event handling?
            } catch (VMDisconnectedException e) {
                log.severe("Virtual Machine is disconnected: " + e.getMessage());
                handleVMDisconnectedException();
            } catch (InterruptedException ignore) {
                log.severe("Interrupted.");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    protected void prepareVm() {
        launchVm();
        vm.setDebugTraceMode(VirtualMachine.TRACE_NONE);
        enableClassPrepareRequest();
    }

    protected void launchVm() {
        final LaunchingConnector launchingConnector = launchingConnectorService.connect();
        log.info(String.format("Connector:%n%s", PrettyUtil.pretty(launchingConnector)));
        final Map<String, String> values = Collections.singletonMap("main", debugClass.getName());
        log.info(String.format("Connector values: %s.", values));
        final Map<String, Connector.Argument> arguments = launchingConnectorService.arguments(launchingConnector, values);
        try {
            vm = launchingConnector.launch(arguments);
            connected = true;
        } catch (IOException e) {
            throw new Error(String.format("Unable to launch target VM: %s.", e));
        } catch (IllegalConnectorArgumentsException e) {
            throw new Error(String.format("Internal error: %s.", e));
        } catch (VMStartException e) {
            throw new Error(String.format("Target VM failed to initialize: %s.", e.getMessage()));
        }
    }

    protected void handleEvent(Event event) {
        if (event instanceof VMStartEvent) {
            handleVmStartEvent((VMStartEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            handleClassPrepareEvent((ClassPrepareEvent) event);
        } else if (event instanceof VMDeathEvent) {
            handleVmDeathEvent((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            handleVmDisconnectEvent((VMDisconnectEvent) event);
        } else {
            log.info(String.format("Event: %s.", event));
        }
//        if (event instanceof BreakpointEvent) {
//            displayVariables((BreakpointEvent) event);
//        }
    }

    protected void handleVmStartEvent(VMStartEvent event) {
        log.info("VMStartEvent.");
    }

    protected void handleClassPrepareEvent(ClassPrepareEvent event) {
        final EventRequestManager manager = vm.eventRequestManager();
        final List<Field> fields = event.referenceType().visibleFields();
        for (Field field : fields) {
            final ModificationWatchpointRequest request = manager.createModificationWatchpointRequest(field);
            request.setSuspendPolicy(EventRequest.SUSPEND_NONE);
            request.enable();
        }
//        ClassType classType = (ClassType) event.referenceType();
//        for (int lineNumber : breakPointLines) {
//            List<Location> locations = classType.locationsOfLine(lineNumber);
//            if (!locations.isEmpty()) {
//                Location location = locations.get(0);
//                EventRequestManager eventRequestManager = vm.eventRequestManager();
//                BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
//                breakpointRequest.enable();
//            }
//        }
    }

    protected void handleVmDeathEvent(VMDeathEvent event) {
        died = true;
        log.info("-- The application exited --");
    }

    protected void handleVmDisconnectEvent(VMDisconnectEvent event) {
        connected = false;
        if (!died) {
            log.info("-- The application has been disconnected --");
        }
    }

    /***
     * A VMDisconnectedException has happened while dealing with
     * another event. We need to flush the event queue, dealing only
     * with exit events (VMDeath, VMDisconnect) so that we terminate
     * correctly.
     */
    protected synchronized void handleVMDisconnectedException() {
        final EventQueue eventQueue = vm.eventQueue();
        while (connected) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    if (event instanceof VMDeathEvent) {
                        handleVmDeathEvent((VMDeathEvent) event);
                    } else if (event instanceof VMDisconnectEvent) {
                        handleVmDisconnectEvent((VMDisconnectEvent) event);
                    }
                }
                vm.resume(); //FIXME: call it after all or after each event handling?
            } catch (InterruptedException ignore) {
                log.severe("Interrupted.");
            }
        }
    }


    public void enableClassPrepareRequest() {
        EventRequestManager eventRequestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        String debugClassName = debugClass.getName();
        classPrepareRequest.addClassFilter(debugClassName);
        classPrepareRequest.enable();
    }

//    public void displayVariables(LocatableEvent event) {
//        final StackFrame stackFrame = event.thread().frame(0);
//        final Location stackLocation = stackFrame.location();
//        final String stackLocationName = stackLocation.toString();
//        final String debugClassName = debugClass.getName();
//        if (stackLocationName.contains(debugClassName)) {
//            final List<LocalVariable> visibleVariables = stackFrame.visibleVariables();
//            final Map<LocalVariable, Value> variableValues = stackFrame.getValues(visibleVariables);
//            final StringJoiner joiner = new StringJoiner(System.lineSeparator())
//                    .add("Variables at " + stackLocationName + " > ");
//            for (Map.Entry<LocalVariable, Value> entry : variableValues.entrySet()) {
//                LocalVariable variable = entry.getKey();
//                String variableName = variable.name();
//                Value variableValue = entry.getValue();
//                joiner.add("\t" + variableName + " = " + variableValue);
//            }
//            log.info(joiner.toString());
//        }
//    }
}
