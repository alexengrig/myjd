package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.event.*;

import java.util.function.Consumer;

public interface YouthEventSubscriptionManager {
    void subscribeOnException(Consumer<YouthExceptionEvent> listener);

    void notifyOfException(YouthExceptionEvent event);

    void subscribeOnBreakpoint(Consumer<YouthBreakpointEvent> listener);

    void notifyOfBreakpoint(YouthBreakpointEvent event);

    void subscribeOnStep(Consumer<YouthStepEvent> listener);

    void notifyOfStep(YouthStepEvent event);

    void subscribeOnVMDeath(Consumer<YouthVMDeathEvent> listener);

    void notifyOfVMDeath(YouthVMDeathEvent event);

    void subscribeOnVMDisconnect(Consumer<YouthVMDisconnectEvent> listener);

    void notifyOfVMDisconnect(YouthVMDisconnectEvent event);

    void subscribeOnVMStart(Consumer<YouthVMStartEvent> listener);

    void notifyOfVMStart(YouthVMStartEvent event);

    void subscribeOnClassUnload(Consumer<YouthClassUnloadEvent> listener);

    void notifyOfClassUnload(YouthClassUnloadEvent event);

    void subscribeOnClassPrepare(Consumer<YouthClassPrepareEvent> listener);

    void notifyOfClassPrepare(YouthClassPrepareEvent event);

    void subscribeOnMethodExit(Consumer<YouthMethodExitEvent> listener);

    void notifyOfMethodExit(YouthMethodExitEvent event);

    void subscribeOnMethodEntry(Consumer<YouthMethodEntryEvent> listener);

    void notifyOfMethodEntry(YouthMethodEntryEvent event);

    void subscribeOnAccessWatchpoint(Consumer<YouthAccessWatchpointEvent> listener);

    void notifyOfAccessWatchpoint(YouthAccessWatchpointEvent event);

    void subscribeOnModificationWatchpoint(Consumer<YouthModificationWatchpointEvent> listener);

    void notifyOfModificationWatchpoint(YouthModificationWatchpointEvent event);

    void subscribeOnThreadDeath(Consumer<YouthThreadDeathEvent> listener);

    void notifyOfThreadDeath(YouthThreadDeathEvent event);

    void subscribeOnThreadStart(Consumer<YouthThreadStartEvent> listener);

    void notifyOfThreadStart(YouthThreadStartEvent event);

    void subscribeOnMonitorWaited(Consumer<YouthMonitorWaitedEvent> listener);

    void notifyOfMonitorWaited(YouthMonitorWaitedEvent event);

    void subscribeOnMonitorWait(Consumer<YouthMonitorWaitEvent> listener);

    void notifyOfMonitorWait(YouthMonitorWaitEvent event);

    void subscribeOnMonitorContendedEntered(Consumer<YouthMonitorContendedEnteredEvent> listener);

    void notifyOfMonitorContendedEntered(YouthMonitorContendedEnteredEvent event);

    void subscribeOnMonitorContendedEnter(Consumer<YouthMonitorContendedEnterEvent> listener);

    void notifyOfMonitorContendedEnter(YouthMonitorContendedEnterEvent event);
}
