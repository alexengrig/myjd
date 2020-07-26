package dev.alexengrig.myjdi.handle;

import com.sun.jdi.VMDisconnectedException;
import dev.alexengrig.myjdi.event.*;
import dev.alexengrig.myjdi.queue.YouthEventIterator;
import dev.alexengrig.myjdi.queue.YouthEventQueue;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.util.logging.Logger;

public class MyEventHandler implements YouthEventHandler {
    private static final Logger log = Logger.getLogger(MyEventHandler.class.getName());

    protected final YouthVirtualMachine virtualMachine;
    protected boolean running;
    protected boolean disconnected;
    protected boolean died;
    protected boolean interrupted;

    public MyEventHandler(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    @Override
    public void run() {
        running = true;
        disconnected = died = interrupted = false;
        final YouthEventQueue queue = virtualMachine.eventQueue();
        YouthEventIterator iterator;
        YouthEvent event;
        while (running) {
            try {
                iterator = queue.remove().eventIterator();
                while (iterator.hasNext()) {
                    event = iterator.next();
                    event.accept(this);
                }
                virtualMachine.resume();
            } catch (VMDisconnectedException ignore) {
                handleVMDisconnectedException();
            } catch (InterruptedException ignore) {
                handleInterruptedException();
                Thread.currentThread().interrupt();
            }
        }
    }

    /***
     * A VMDisconnectedException has happened while dealing with
     * another event. We need to flush the event queue, dealing only
     * with exit events (VMDeath, VMDisconnect) so that we terminate
     * correctly.
     */
    protected synchronized void handleVMDisconnectedException() {
        FlushHandler flushHandler = new FlushHandler();
        while (running) {
            try {
                YouthEventIterator iterator = virtualMachine.eventQueue().remove().eventIterator();
                while (iterator.hasNext()) {
                    YouthEvent event = iterator.next();
                    event.accept(flushHandler);
                }
                virtualMachine.resume();
            } catch (InterruptedException ignore) {
                handleInterruptedException();
                Thread.currentThread().interrupt();
            }
        }
        log.warning("VM disconnected exception.");
    }

    protected void handleInterruptedException() {
        running = false;
        interrupted = true;
        log.warning("Interrupted exception.");
    }

    @Override
    public void handleVmStart(YouthVMStartEvent event) {
        log.info("VM is started.");
        virtualMachine.eventSubscriptionManager().notifyOfVMStart(event);
    }

    @Override
    public void handleVmDeath(YouthVMDeathEvent event) {
        died = true;
        running = false;
        virtualMachine.eventSubscriptionManager().notifyOfVMDeath(event);
        log.info("VM is died.");
    }

    @Override
    public void handleVmDisconnect(YouthVMDisconnectEvent event) {
        disconnected = !died;
        running = false;
        virtualMachine.eventSubscriptionManager().notifyOfVMDisconnect(event);
        if (disconnected) {
            log.info("VM is disconnected.");
        }
    }

    @Override
    public void handleException(YouthExceptionEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfException(event);
    }

    @Override
    public void handleBreakpoint(YouthBreakpointEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfBreakpoint(event);
    }

    @Override
    public void handleStep(YouthStepEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfStep(event);
    }

    @Override
    public void handleAccessWatchpoint(YouthAccessWatchpointEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfAccessWatchpoint(event);
    }

    @Override
    public void handleModificationWatchpoint(YouthModificationWatchpointEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfModificationWatchpoint(event);
    }

    @Override
    public void handleMethodExit(YouthMethodExitEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfMethodExit(event);
    }

    @Override
    public void handleMethodEntry(YouthMethodEntryEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfMethodEntry(event);
    }

    @Override
    public void handleMonitorWaited(YouthMonitorWaitedEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfMonitorWaited(event);
    }

    @Override
    public void handleMonitorWait(YouthMonitorWaitEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfMonitorWait(event);
    }

    @Override
    public void handleMonitorContendedEntered(YouthMonitorContendedEnteredEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfMonitorContendedEntered(event);
    }

    @Override
    public void handleMonitorContendedEnter(YouthMonitorContendedEnterEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfMonitorContendedEnter(event);
    }

    @Override
    public void handleClassUnload(YouthClassUnloadEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfClassUnload(event);
    }

    @Override
    public void handleClassPrepare(YouthClassPrepareEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfClassPrepare(event);
    }

    @Override
    public void handleThreadDeath(YouthThreadDeathEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfThreadDeath(event);
    }

    @Override
    public void handleThreadStart(YouthThreadStartEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfThreadStart(event);
    }

    protected class FlushHandler implements YouthEventHandler {
        @Override
        public void handleVmDeath(YouthVMDeathEvent event) {
            MyEventHandler.this.handleVmDeath(event);
        }

        @Override
        public void handleVmDisconnect(YouthVMDisconnectEvent event) {
            MyEventHandler.this.handleVmDisconnect(event);
        }

        @Override
        public void run() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void handleException(YouthExceptionEvent event) {
            // skipping
        }

        @Override
        public void handleBreakpoint(YouthBreakpointEvent event) {
            // skipping
        }

        @Override
        public void handleStep(YouthStepEvent event) {
            // skipping
        }

        @Override
        public void handleAccessWatchpoint(YouthAccessWatchpointEvent event) {
            // skipping
        }

        @Override
        public void handleModificationWatchpoint(YouthModificationWatchpointEvent event) {
            // skipping
        }

        @Override
        public void handleMethodExit(YouthMethodExitEvent event) {
            // skipping
        }

        @Override
        public void handleMethodEntry(YouthMethodEntryEvent event) {
            // skipping
        }

        @Override
        public void handleMonitorWaited(YouthMonitorWaitedEvent event) {
            // skipping
        }

        @Override
        public void handleMonitorWait(YouthMonitorWaitEvent event) {
            // skipping
        }

        @Override
        public void handleMonitorContendedEntered(YouthMonitorContendedEnteredEvent event) {
            // skipping
        }

        @Override
        public void handleMonitorContendedEnter(YouthMonitorContendedEnterEvent event) {
            // skipping
        }

        @Override
        public void handleClassUnload(YouthClassUnloadEvent event) {
            // skipping
        }

        @Override
        public void handleClassPrepare(YouthClassPrepareEvent event) {
            // skipping
        }

        @Override
        public void handleThreadDeath(YouthThreadDeathEvent event) {
            // skipping
        }

        @Override
        public void handleThreadStart(YouthThreadStartEvent event) {
            // skipping
        }

        @Override
        public void handleVmStart(YouthVMStartEvent event) {
            // skipping
        }
    }
}
