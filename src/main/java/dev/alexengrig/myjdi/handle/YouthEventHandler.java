package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.*;

public interface YouthEventHandler extends Runnable {
    void handleException(YouthExceptionEvent event);

    void handleBreakpoint(YouthBreakpointEvent event);

    void handleStep(YouthStepEvent event);

    void handleAccessWatchpoint(YouthAccessWatchpointEvent event);

    void handleModificationWatchpoint(YouthModificationWatchpointEvent event);

    void handleMethodExit(YouthMethodExitEvent event);

    void handleMethodEntry(YouthMethodEntryEvent event);

    void handleMonitorWaited(YouthMonitorWaitedEvent event);

    void handleMonitorWait(YouthMonitorWaitEvent event);

    void handleMonitorContendedEntered(YouthMonitorContendedEnteredEvent event);

    void handleMonitorContendedEnter(YouthMonitorContendedEnterEvent event);

    void handleClassUnload(YouthClassUnloadEvent event);

    void handleClassPrepare(YouthClassPrepareEvent event);

    void handleThreadDeath(YouthThreadDeathEvent event);

    void handleThreadStart(YouthThreadStartEvent event);

    void handleVmDeath(YouthVMDeathEvent event);

    void handleVmDisconnect(YouthVMDisconnectEvent event);

    void handleVmStart(YouthVMStartEvent event);
}
