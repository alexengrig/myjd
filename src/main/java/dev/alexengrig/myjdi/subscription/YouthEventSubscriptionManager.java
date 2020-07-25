package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.event.*;

public interface YouthEventSubscriptionManager {
    void subscribeOnException(YouthEventSubscriber<YouthExceptionEvent> listener);

    void notifyOfException(YouthExceptionEvent event);

    void subscribeOnBreakpoint(YouthEventSubscriber<YouthBreakpointEvent> listener);

    void notifyOfBreakpoint(YouthBreakpointEvent event);

    void subscribeOnStep(YouthEventSubscriber<YouthStepEvent> listener);

    void notifyOfStep(YouthStepEvent event);

    void subscribeOnVMDeath(YouthEventSubscriber<YouthVMDeathEvent> listener);

    void notifyOfVMDeath(YouthVMDeathEvent event);

    void subscribeOnVMDisconnect(YouthEventSubscriber<YouthVMDisconnectEvent> listener);

    void notifyOfVMDisconnect(YouthVMDisconnectEvent event);

    void subscribeOnVMStart(YouthEventSubscriber<YouthVMStartEvent> listener);

    void notifyOfVMStart(YouthVMStartEvent event);

    void subscribeOnClassUnload(YouthEventSubscriber<YouthClassUnloadEvent> listener);

    void notifyOfClassUnload(YouthClassUnloadEvent event);

    void subscribeOnClassPrepare(YouthEventSubscriber<YouthClassPrepareEvent> listener);

    void notifyOfClassPrepare(YouthClassPrepareEvent event);

    void subscribeOnMethodExit(YouthEventSubscriber<YouthMethodExitEvent> listener);

    void notifyOfMethodExit(YouthMethodExitEvent event);

    void subscribeOnMethodEntry(YouthEventSubscriber<YouthMethodEntryEvent> listener);

    void notifyOfMethodEntry(YouthMethodEntryEvent event);

    void subscribeOnAccessWatchpoint(YouthEventSubscriber<YouthAccessWatchpointEvent> listener);

    void notifyOfAccessWatchpoint(YouthAccessWatchpointEvent event);

    void subscribeOnModificationWatchpoint(YouthEventSubscriber<YouthModificationWatchpointEvent> listener);

    void notifyOfModificationWatchpoint(YouthModificationWatchpointEvent event);

    void subscribeOnThreadDeath(YouthEventSubscriber<YouthThreadDeathEvent> listener);

    void notifyOfThreadDeath(YouthThreadDeathEvent event);

    void subscribeOnThreadStart(YouthEventSubscriber<YouthThreadStartEvent> listener);

    void notifyOfThreadStart(YouthThreadStartEvent event);

    void subscribeOnMonitorWaited(YouthEventSubscriber<YouthMonitorWaitedEvent> listener);

    void notifyOfMonitorWaited(YouthMonitorWaitedEvent event);

    void subscribeOnMonitorWait(YouthEventSubscriber<YouthMonitorWaitEvent> listener);

    void notifyOfMonitorWait(YouthMonitorWaitEvent event);

    void subscribeOnMonitorContendedEntered(YouthEventSubscriber<YouthMonitorContendedEnteredEvent> listener);

    void notifyOfMonitorContendedEntered(YouthMonitorContendedEnteredEvent event);

    void subscribeOnMonitorContendedEnter(YouthEventSubscriber<YouthMonitorContendedEnterEvent> listener);

    void notifyOfMonitorContendedEnter(YouthMonitorContendedEnterEvent event);
}
