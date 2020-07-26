package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.event.*;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.util.ArrayList;
import java.util.List;

public class MyEventSubscriptionManager implements YouthEventSubscriptionManager {
    protected final YouthVirtualMachine virtualMachine;

    protected List<YouthEventSubscriber<YouthExceptionEvent>> exceptionSubscribers;
    protected List<YouthEventSubscriber<YouthBreakpointEvent>> breakpointSubscribers;
    protected List<YouthEventSubscriber<YouthStepEvent>> stepSubscribers;
    protected List<YouthEventSubscriber<YouthVMDeathEvent>> vMDeathSubscribers;
    protected List<YouthEventSubscriber<YouthVMDisconnectEvent>> vMDisconnectSubscribers;
    protected List<YouthEventSubscriber<YouthVMStartEvent>> vMStartSubscribers;
    protected List<YouthEventSubscriber<YouthClassUnloadEvent>> classUnloadSubscribers;
    protected List<YouthEventSubscriber<YouthClassPrepareEvent>> classPrepareSubscribers;
    protected List<YouthEventSubscriber<YouthMethodExitEvent>> methodExitSubscribers;
    protected List<YouthEventSubscriber<YouthMethodEntryEvent>> methodEntrySubscribers;
    protected List<YouthEventSubscriber<YouthAccessWatchpointEvent>> accessWatchpointSubscribers;
    protected List<YouthEventSubscriber<YouthModificationWatchpointEvent>> modificationWatchpointSubscribers;
    protected List<YouthEventSubscriber<YouthThreadDeathEvent>> threadDeathSubscribers;
    protected List<YouthEventSubscriber<YouthThreadStartEvent>> threadStartSubscribers;
    protected List<YouthEventSubscriber<YouthMonitorWaitedEvent>> monitorWaitedSubscribers;
    protected List<YouthEventSubscriber<YouthMonitorWaitEvent>> monitorWaitSubscribers;
    protected List<YouthEventSubscriber<YouthMonitorContendedEnteredEvent>> monitorContendedEnteredSubscribers;
    protected List<YouthEventSubscriber<YouthMonitorContendedEnterEvent>> monitorContendedEnterSubscribers;

    public MyEventSubscriptionManager(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        this.exceptionSubscribers = new ArrayList<>();
        this.breakpointSubscribers = new ArrayList<>();
        this.stepSubscribers = new ArrayList<>();
        this.vMDeathSubscribers = new ArrayList<>();
        this.vMDisconnectSubscribers = new ArrayList<>();
        this.vMStartSubscribers = new ArrayList<>();
        this.classUnloadSubscribers = new ArrayList<>();
        this.classPrepareSubscribers = new ArrayList<>();
        this.methodExitSubscribers = new ArrayList<>();
        this.methodEntrySubscribers = new ArrayList<>();
        this.accessWatchpointSubscribers = new ArrayList<>();
        this.modificationWatchpointSubscribers = new ArrayList<>();
        this.threadDeathSubscribers = new ArrayList<>();
        this.threadStartSubscribers = new ArrayList<>();
        this.monitorWaitedSubscribers = new ArrayList<>();
        this.monitorWaitSubscribers = new ArrayList<>();
        this.monitorContendedEnteredSubscribers = new ArrayList<>();
        this.monitorContendedEnterSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribeOnException(YouthEventSubscriber<YouthExceptionEvent> subscriber) {
        exceptionSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfException(YouthExceptionEvent event) {
        exceptionSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnBreakpoint(YouthEventSubscriber<YouthBreakpointEvent> subscriber) {
        breakpointSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfBreakpoint(YouthBreakpointEvent event) {
        breakpointSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnStep(YouthEventSubscriber<YouthStepEvent> subscriber) {
        stepSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfStep(YouthStepEvent event) {
        stepSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnVMDeath(YouthEventSubscriber<YouthVMDeathEvent> subscriber) {
        vMDeathSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfVMDeath(YouthVMDeathEvent event) {
        vMDeathSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnVMDisconnect(YouthEventSubscriber<YouthVMDisconnectEvent> subscriber) {
        vMDisconnectSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfVMDisconnect(YouthVMDisconnectEvent event) {
        vMDisconnectSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnVMStart(YouthEventSubscriber<YouthVMStartEvent> subscriber) {
        vMStartSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfVMStart(YouthVMStartEvent event) {
        vMStartSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnClassUnload(YouthEventSubscriber<YouthClassUnloadEvent> subscriber) {
        classUnloadSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfClassUnload(YouthClassUnloadEvent event) {
        classUnloadSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnClassPrepare(YouthEventSubscriber<YouthClassPrepareEvent> subscriber) {
        classPrepareSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfClassPrepare(YouthClassPrepareEvent event) {
        classPrepareSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnMethodExit(YouthEventSubscriber<YouthMethodExitEvent> subscriber) {
        methodExitSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfMethodExit(YouthMethodExitEvent event) {
        methodExitSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnMethodEntry(YouthEventSubscriber<YouthMethodEntryEvent> subscriber) {
        methodEntrySubscribers.add(subscriber);
    }

    @Override
    public void notifyOfMethodEntry(YouthMethodEntryEvent event) {
        methodEntrySubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnAccessWatchpoint(YouthEventSubscriber<YouthAccessWatchpointEvent> subscriber) {
        accessWatchpointSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfAccessWatchpoint(YouthAccessWatchpointEvent event) {
        accessWatchpointSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnModificationWatchpoint(YouthEventSubscriber<YouthModificationWatchpointEvent> subscriber) {
        modificationWatchpointSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfModificationWatchpoint(YouthModificationWatchpointEvent event) {
        modificationWatchpointSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnThreadDeath(YouthEventSubscriber<YouthThreadDeathEvent> subscriber) {
        threadDeathSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfThreadDeath(YouthThreadDeathEvent event) {
        threadDeathSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnThreadStart(YouthEventSubscriber<YouthThreadStartEvent> subscriber) {
        threadStartSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfThreadStart(YouthThreadStartEvent event) {
        threadStartSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnMonitorWaited(YouthEventSubscriber<YouthMonitorWaitedEvent> subscriber) {
        monitorWaitedSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfMonitorWaited(YouthMonitorWaitedEvent event) {
        monitorWaitedSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnMonitorWait(YouthEventSubscriber<YouthMonitorWaitEvent> subscriber) {
        monitorWaitSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfMonitorWait(YouthMonitorWaitEvent event) {
        monitorWaitSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnMonitorContendedEntered(YouthEventSubscriber<YouthMonitorContendedEnteredEvent> subscriber) {
        monitorContendedEnteredSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfMonitorContendedEntered(YouthMonitorContendedEnteredEvent event) {
        monitorContendedEnteredSubscribers.forEach(subscriber -> subscriber.accept(event));
    }

    @Override
    public void subscribeOnMonitorContendedEnter(YouthEventSubscriber<YouthMonitorContendedEnterEvent> subscriber) {
        monitorContendedEnterSubscribers.add(subscriber);
    }

    @Override
    public void notifyOfMonitorContendedEnter(YouthMonitorContendedEnterEvent event) {
        monitorContendedEnterSubscribers.forEach(subscriber -> subscriber.accept(event));
    }
}
