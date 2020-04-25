package dev.alexengrig.myjdi;

import com.sun.jdi.*;
import com.sun.jdi.event.*;

import java.util.logging.Logger;

public class EventQueueHandler implements Runnable {
    private static final Logger log = Logger.getLogger(EventQueueHandler.class.getSimpleName());

    private final EventQueue eventQueue;
    private boolean handling;
    private boolean vmDied;

    public EventQueueHandler(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void run() {
        handling = true;
        EventSet eventSet;
        while (handling) {
            try {
                eventSet = eventQueue.remove();
                for (Event event : eventSet) {
                    handleEvent(event);
                }
                eventQueue.virtualMachine().resume();
            } catch (InterruptedException e) {
                handleInterruptedException(e);
            } catch (VMDisconnectedException e) {
                handleVMDisconnectedException(e);
            }
        }
    }

    private void handleInterruptedException(InterruptedException exception) {
    }

    /***
     * A VMDisconnectedException has happened while dealing with
     * another event. We need to flush the event queue, dealing only
     * with exit events (VMDeath, VMDisconnect) so that we terminate
     * correctly.
     */
    private synchronized void handleVMDisconnectedException(VMDisconnectedException exception) {
        while (handling) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    if (event instanceof VMDeathEvent) {
                        handleVmDeathEvent((VMDeathEvent) event);
                    } else if (event instanceof VMDisconnectEvent) {
                        handleVmDisconnectEvent((VMDisconnectEvent) event);
                    }
                }
                eventQueue.virtualMachine().resume();
            } catch (InterruptedException e) {
                handleInterruptedException(e);
            }
        }
    }

//    Events

    private void handleEvent(Event event) {
        if (event instanceof ExceptionEvent) {
            handleExceptionEvent((ExceptionEvent) event);
        } else if (event instanceof BreakpointEvent) {
            handleBreakpointEvent((BreakpointEvent) event);
        } else if (event instanceof StepEvent) {
            handleStepEvent((StepEvent) event);
        }
        // watchpoint events
        else if (event instanceof AccessWatchpointEvent) {
            handleAccessWatchpointEvent((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            handleModificationWatchpointEvent((ModificationWatchpointEvent) event);
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
        } else if (event instanceof MonitorContendedEnteredEvent) {
            handleMonitorContendedEnteredEvent((MonitorContendedEnteredEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            handleMonitorContendedEnterEvent((MonitorContendedEnterEvent) event);
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
        }
        // unexpected
        else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

    private void handleExceptionEvent(ExceptionEvent event) {
        final ObjectReference exception = event.exception();
        final Location location = event.catchLocation();
        log.info(String.format("Exception: %s; in location: %s.", exception, location));
    }

    private void handleBreakpointEvent(BreakpointEvent event) {
        log.info(String.format("Breakpoint: %s.", event.location()));
    }

    private void handleStepEvent(StepEvent event) {
        log.info(String.format("Step: %s.", event.location()));
    }

//    Watchpoint events

    private void handleAccessWatchpointEvent(AccessWatchpointEvent event) {
        final Field field = event.field();
        final Value value = event.valueCurrent();
        log.info(String.format("Field accessed: %s; with value: %s.", field, value));
    }

    private void handleModificationWatchpointEvent(ModificationWatchpointEvent event) {
        final Field field = event.field();
        final Value oldValue = event.valueCurrent();
        final Value newValue = event.valueToBe();
        log.info(String.format("Field modified: %s; from: %s; to: %s.", field, oldValue, newValue));

    }

//    Method events

    private void handleMethodExitEvent(MethodExitEvent event) {
        final Method method = event.method();
        final Value value = event.returnValue();
        log.info(String.format("Method: %s; return: %s.", method, value));
    }

    private void handleMethodEntryEvent(MethodEntryEvent event) {
        final Method method = event.method();
        log.info(String.format("Method called: %s.", method));
    }

//    Monitor events

    private void handleMonitorWaitedEvent(MonitorWaitedEvent event) {
        log.info("MonitorWaitedEvent."); //TODO: Add implementation
    }

    private void handleMonitorWaitEvent(MonitorWaitEvent event) {
        log.info("MonitorWaitEvent."); //TODO: Add implementation
    }

    private void handleMonitorContendedEnteredEvent(MonitorContendedEnteredEvent event) {
        log.info("MonitorContendedEnteredEvent."); //TODO: Add implementation
    }

    private void handleMonitorContendedEnterEvent(MonitorContendedEnterEvent event) {
        log.info("MonitorContendedEnterEvent."); //TODO: Add implementation
    }

//    Class events

    private void handleClassUnloadEvent(ClassUnloadEvent event) {
        log.info(String.format("Class unloaded: %s.", event.className()));
    }

    private void handleClassPrepareEvent(ClassPrepareEvent event) {
        final ReferenceType type = event.referenceType();
        log.info(String.format("Class prepared: %s.", type.name()));
    }

//    Thread events

    private void handleThreadDeathEvent(ThreadDeathEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("Thread terminated: %s.", thread));
    }

    private void handleThreadStartEvent(ThreadStartEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("Thread started: %s.", thread));
    }

//    Vm events

    private void handleVmDeathEvent(VMDeathEvent event) {
        vmDied = true;
        log.info("VM finished.");
    }

    private void handleVmDisconnectEvent(VMDisconnectEvent event) {
        handling = false;
        if (!vmDied) {
            log.info("VM disconnected.");
        }
    }

    private void handleVmStartEvent(VMStartEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("VM started in thread: %s.", thread));
    }
}
