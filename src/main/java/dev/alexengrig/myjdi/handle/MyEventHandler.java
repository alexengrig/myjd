package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.*;

public interface MyEventHandler {
    void handle(MyEvent event);

    void handleException(MyExceptionEvent event);

    void handleBreakpoint(MyBreakpointEvent event);

    void handleStep(MyStepEvent event);

    void handleAccessWatchpoint(MyAccessWatchpointEvent event);

    void handleModificationWatchpoint(MyModificationWatchpointEvent event);

    void handleMethodExit(MyMethodExitEvent event);

    void handleMethodEntry(MyMethodEntryEvent event);

    void handleMonitorWaited(MyMonitorWaitedEvent event);

    void handleMonitorWait(MyMonitorWaitEvent event);

    void handleMonitorContendedEntered(MyMonitorContendedEnteredEvent event);

    void handleMonitorContendedEnter(MyMonitorContendedEnterEvent event);

    void handleClassUnload(MyClassUnloadEvent event);

    void handleClassPrepare(MyClassPrepareEvent event);

    void handleThreadDeath(MyThreadDeathEvent event);

    void handleThreadStart(MyThreadStartEvent event);

    void handleVmDeath(MyVMDeathEvent event);

    void handleVmDisconnect(MyVMDisconnectEvent event);

    void handleVmStart(MyVMStartEvent event);
}
