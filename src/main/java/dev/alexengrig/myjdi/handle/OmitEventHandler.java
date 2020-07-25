package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.*;

import java.util.logging.Logger;

public abstract class OmitEventHandler implements YouthEventHandler {
    private static final Logger log = Logger.getLogger(OmitEventHandler.class.getSimpleName());

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handle(YouthEvent event) {
        log.info("Omitted event: " + event);
    }

    @Override
    public void handleException(YouthExceptionEvent event) {
        handle(event);
    }

    @Override
    public void handleBreakpoint(YouthBreakpointEvent event) {
        handle(event);
    }

    @Override
    public void handleStep(YouthStepEvent event) {
        handle(event);
    }

    @Override
    public void handleAccessWatchpoint(YouthAccessWatchpointEvent event) {
        handle(event);
    }

    @Override
    public void handleModificationWatchpoint(YouthModificationWatchpointEvent event) {
        handle(event);
    }

    @Override
    public void handleMethodExit(YouthMethodExitEvent event) {
        handle(event);
    }

    @Override
    public void handleMethodEntry(YouthMethodEntryEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorWaited(YouthMonitorWaitedEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorWait(YouthMonitorWaitEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorContendedEntered(YouthMonitorContendedEnteredEvent event) {
        handle(event);
    }

    @Override
    public void handleMonitorContendedEnter(YouthMonitorContendedEnterEvent event) {
        handle(event);
    }

    @Override
    public void handleClassUnload(YouthClassUnloadEvent event) {
        handle(event);
    }

    @Override
    public void handleClassPrepare(YouthClassPrepareEvent event) {
        handle(event);
    }

    @Override
    public void handleThreadDeath(YouthThreadDeathEvent event) {
        handle(event);
    }

    @Override
    public void handleThreadStart(YouthThreadStartEvent event) {
        handle(event);
    }

    @Override
    public void handleVmDeath(YouthVMDeathEvent event) {
        handle(event);
    }

    @Override
    public void handleVmDisconnect(YouthVMDisconnectEvent event) {
        handle(event);
    }

    @Override
    public void handleVmStart(YouthVMStartEvent event) {
        handle(event);
    }
}
