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
        // watchpoint events
        if (event instanceof AccessWatchpointEvent) {
            handleAccessWatchpointEvent((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            handleModificationWatchpointEvent((ModificationWatchpointEvent) event);
        } else if (event instanceof WatchpointEvent) {
            handleWatchpointEvent((WatchpointEvent) event);
        }
        // exception event
        else if (event instanceof ExceptionEvent) {
            handleExceptionEvent((ExceptionEvent) event);
        }
        // breakpoint event
        else if (event instanceof BreakpointEvent) {
            handleBreakpointEvent((BreakpointEvent) event);
        }
        // step event
        else if (event instanceof StepEvent) {
            handleStepEvent((StepEvent) event);
        }
        // method events
        else if (event instanceof MethodExitEvent) {
            handleMethodExitEvent((MethodExitEvent) event);
        } else if (event instanceof MethodEntryEvent) {
            handleMethodEntryEvent((MethodEntryEvent) event);
        }
        // monitor events
        else if (event instanceof MonitorWaitedEvent) {
            handleMonitorWaitedEvent((MonitorWaitedEvent) event);
        } else if (event instanceof MonitorWaitEvent) {
            handleMonitorWaitEvent((MonitorWaitEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            handleMonitorContendedEnterEvent((MonitorContendedEnterEvent) event);
        } else if (event instanceof MonitorContendedEnteredEvent) {
            handleMonitorContendedEnteredEvent((MonitorContendedEnteredEvent) event);
        }
        // locatable event
        else if (event instanceof LocatableEvent) {
            handleLocatableEvent((LocatableEvent) event);
        }
        // class events
        else if (event instanceof ClassUnloadEvent) {
            handleClassUnloadEvent((ClassUnloadEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            handleClassPrepareEvent((ClassPrepareEvent) event);
        }
        // thread events
        else if (event instanceof ThreadDeathEvent) {
            handleThreadDeathEvent((ThreadDeathEvent) event);
        } else if (event instanceof ThreadStartEvent) {
            handleThreadStartEvent((ThreadStartEvent) event);
        }
        // vm events
        else if (event instanceof VMDeathEvent) {
            handleVmDeathEvent((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            handleVmDisconnectEvent((VMDisconnectEvent) event);
        } else if (event instanceof VMStartEvent) {
            handleVmStartEvent((VMStartEvent) event);
        } else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

//    Watchpoint events

    protected void handleAccessWatchpointEvent(AccessWatchpointEvent event) {
        log.info("AccessWatchpointEvent.");
    }

    protected void handleModificationWatchpointEvent(ModificationWatchpointEvent event) {
        log.info("ModificationWatchpointEvent.");
    }

    protected void handleWatchpointEvent(WatchpointEvent event) {
        log.info("WatchpointEvent.");
    }

//    Exception event

    protected void handleExceptionEvent(ExceptionEvent event) {
        log.info("ExceptionEvent.");
    }

//    Breakpoint event

    protected void handleBreakpointEvent(BreakpointEvent event) {
        log.info("BreakpointEvent.");
    }

//    Step event

    protected void handleStepEvent(StepEvent event) {
        log.info("StepEvent.");
    }

//    Method events

    protected void handleMethodExitEvent(MethodExitEvent event) {
        log.info("MethodExitEvent.");
    }

    protected void handleMethodEntryEvent(MethodEntryEvent event) {
        log.info("MethodEntryEvent.");
    }

//    Monitor events

    protected void handleMonitorContendedEnteredEvent(MonitorContendedEnteredEvent event) {
        log.info("MonitorContendedEnteredEvent.");
    }

    protected void handleMonitorContendedEnterEvent(MonitorContendedEnterEvent event) {
        log.info("MonitorContendedEnterEvent.");
    }

    protected void handleMonitorWaitedEvent(MonitorWaitedEvent event) {
        log.info("MonitorWaitedEvent.");
    }

    protected void handleMonitorWaitEvent(MonitorWaitEvent event) {
        log.info("MonitorWaitEvent.");
    }

// Locatable event

    protected void handleLocatableEvent(LocatableEvent event) {
        log.info("LocatableEvent.");
    }

//    Class events

    protected void handleClassUnloadEvent(ClassUnloadEvent event) {
        log.info("ClassUnloadEvent.");
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

//    Thread events

    protected void handleThreadDeathEvent(ThreadDeathEvent event) {
        log.info("ThreadDeathEvent.");
    }

    protected void handleThreadStartEvent(ThreadStartEvent event) {
        log.info("ThreadStartEvent.");
    }

//    Vm events

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

    protected void handleVmStartEvent(VMStartEvent event) {
        log.info("VMStartEvent.");
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
