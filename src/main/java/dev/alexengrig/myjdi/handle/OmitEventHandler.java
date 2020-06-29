package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.*;

import java.util.logging.Logger;

public class OmitEventHandler implements MyEventHandler {
    private static final Logger log = Logger.getLogger(OmitEventHandler.class.getSimpleName());

    @Override
    public void handle(MyEvent event) {
        log.info("Omitted event: " + event);
    }

    @Override
    public void handleException(MyExceptionEvent event) {
        handle(event);
    }

    @Override
    public void handleBreakpoint(MyBreakpointEvent event) {
        handle(event);
    }

    @Override
    public void handleStep(MyStepEvent event) {
        handle(event);
    }

    @Override
    public void handleAccessWatchpoint(MyAccessWatchpointEvent event) {
        handle(event);
    }

    @Override
    public void handleModificationWatchpoint(MyModificationWatchpointEvent event) {
        handle(event);
    }

    @Override
    public void handleMethodExit(MyMethodExitEvent event) {
        handle(event);
    }

    @Override
    public void handleMethodEntry(MyMethodEntryEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorWaited(MyMonitorWaitedEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorWait(MyMonitorWaitEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorContendedEntered(MyMonitorContendedEnteredEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorContendedEnter(MyMonitorContendedEnterEvent event) {
        handle(event);
    }

    @Override
    public void handleClassUnload(MyClassUnloadEvent event) {
        handle(event);
    }

    @Override
    public void handleClassPrepare(MyClassPrepareEvent event) {
        handle(event);
    }

    @Override
    public void handleThreadDeath(MyThreadDeathEvent event) {
        handle(event);
    }

    @Override
    public void handleThreadStart(MyThreadStartEvent event) {
        handle(event);
    }

    @Override
    public void handleVmDeath(MyVMDeathEvent event) {
        handle(event);
    }

    @Override
    public void handleVmDisconnect(MyVMDisconnectEvent event) {
        handle(event);
    }

    @Override
    public void handleVmStart(MyVMStartEvent event) {
        handle(event);
    }
}
