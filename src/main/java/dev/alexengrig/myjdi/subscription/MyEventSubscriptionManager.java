package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.YouthVirtualMachine;
import dev.alexengrig.myjdi.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyEventSubscriptionManager implements YouthEventSubscriptionManager {
    protected final YouthVirtualMachine virtualMachine;

    protected List<Consumer<YouthExceptionEvent>> exceptionListeners;
    protected List<Consumer<YouthBreakpointEvent>> breakpointListeners;
    protected List<Consumer<YouthStepEvent>> stepListeners;
    protected List<Consumer<YouthVMDeathEvent>> vMDeathListeners;
    protected List<Consumer<YouthVMDisconnectEvent>> vMDisconnectListeners;
    protected List<Consumer<YouthVMStartEvent>> vMStartListeners;
    protected List<Consumer<YouthClassUnloadEvent>> classUnloadListeners;
    protected List<Consumer<YouthClassPrepareEvent>> classPrepareListeners;
    protected List<Consumer<YouthMethodExitEvent>> methodExitListeners;
    protected List<Consumer<YouthMethodEntryEvent>> methodEntryListeners;
    protected List<Consumer<YouthAccessWatchpointEvent>> accessWatchpointListeners;
    protected List<Consumer<YouthModificationWatchpointEvent>> modificationWatchpointListeners;
    protected List<Consumer<YouthThreadDeathEvent>> threadDeathListeners;
    protected List<Consumer<YouthThreadStartEvent>> threadStartListeners;
    protected List<Consumer<YouthMonitorWaitedEvent>> monitorWaitedListeners;
    protected List<Consumer<YouthMonitorWaitEvent>> monitorWaitListeners;
    protected List<Consumer<YouthMonitorContendedEnteredEvent>> monitorContendedEnteredListeners;
    protected List<Consumer<YouthMonitorContendedEnterEvent>> monitorContendedEnterListeners;

    public MyEventSubscriptionManager(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        this.exceptionListeners = new ArrayList<>();
        this.breakpointListeners = new ArrayList<>();
        this.stepListeners = new ArrayList<>();
        this.vMDeathListeners = new ArrayList<>();
        this.vMDisconnectListeners = new ArrayList<>();
        this.vMStartListeners = new ArrayList<>();
        this.classUnloadListeners = new ArrayList<>();
        this.classPrepareListeners = new ArrayList<>();
        this.methodExitListeners = new ArrayList<>();
        this.methodEntryListeners = new ArrayList<>();
        this.accessWatchpointListeners = new ArrayList<>();
        this.modificationWatchpointListeners = new ArrayList<>();
        this.threadDeathListeners = new ArrayList<>();
        this.threadStartListeners = new ArrayList<>();
        this.monitorWaitedListeners = new ArrayList<>();
        this.monitorWaitListeners = new ArrayList<>();
        this.monitorContendedEnteredListeners = new ArrayList<>();
        this.monitorContendedEnterListeners = new ArrayList<>();

    }

    @Override
    public void subscribeOnException(Consumer<YouthExceptionEvent> listener) {
        exceptionListeners.add(listener);
    }

    @Override
    public void notifyOfException(YouthExceptionEvent event) {
        exceptionListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnBreakpoint(Consumer<YouthBreakpointEvent> listener) {
        breakpointListeners.add(listener);
    }

    @Override
    public void notifyOfBreakpoint(YouthBreakpointEvent event) {
        breakpointListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnStep(Consumer<YouthStepEvent> listener) {
        stepListeners.add(listener);
    }

    @Override
    public void notifyOfStep(YouthStepEvent event) {
        stepListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnVMDeath(Consumer<YouthVMDeathEvent> listener) {
        vMDeathListeners.add(listener);
    }

    @Override
    public void notifyOfVMDeath(YouthVMDeathEvent event) {
        vMDeathListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnVMDisconnect(Consumer<YouthVMDisconnectEvent> listener) {
        vMDisconnectListeners.add(listener);
    }

    @Override
    public void notifyOfVMDisconnect(YouthVMDisconnectEvent event) {
        vMDisconnectListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnVMStart(Consumer<YouthVMStartEvent> listener) {
        vMStartListeners.add(listener);
    }

    @Override
    public void notifyOfVMStart(YouthVMStartEvent event) {
        vMStartListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnClassUnload(Consumer<YouthClassUnloadEvent> listener) {
        classUnloadListeners.add(listener);
    }

    @Override
    public void notifyOfClassUnload(YouthClassUnloadEvent event) {
        classUnloadListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnClassPrepare(Consumer<YouthClassPrepareEvent> listener) {
        classPrepareListeners.add(listener);
    }

    @Override
    public void notifyOfClassPrepare(YouthClassPrepareEvent event) {
        classPrepareListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnMethodExit(Consumer<YouthMethodExitEvent> listener) {
        methodExitListeners.add(listener);
    }

    @Override
    public void notifyOfMethodExit(YouthMethodExitEvent event) {
        methodExitListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnMethodEntry(Consumer<YouthMethodEntryEvent> listener) {
        methodEntryListeners.add(listener);
    }

    @Override
    public void notifyOfMethodEntry(YouthMethodEntryEvent event) {
        methodEntryListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnAccessWatchpoint(Consumer<YouthAccessWatchpointEvent> listener) {
        accessWatchpointListeners.add(listener);
    }

    @Override
    public void notifyOfAccessWatchpoint(YouthAccessWatchpointEvent event) {
        accessWatchpointListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnModificationWatchpoint(Consumer<YouthModificationWatchpointEvent> listener) {
        modificationWatchpointListeners.add(listener);
    }

    @Override
    public void notifyOfModificationWatchpoint(YouthModificationWatchpointEvent event) {
        modificationWatchpointListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnThreadDeath(Consumer<YouthThreadDeathEvent> listener) {
        threadDeathListeners.add(listener);
    }

    @Override
    public void notifyOfThreadDeath(YouthThreadDeathEvent event) {
        threadDeathListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnThreadStart(Consumer<YouthThreadStartEvent> listener) {
        threadStartListeners.add(listener);
    }

    @Override
    public void notifyOfThreadStart(YouthThreadStartEvent event) {
        threadStartListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnMonitorWaited(Consumer<YouthMonitorWaitedEvent> listener) {
        monitorWaitedListeners.add(listener);
    }

    @Override
    public void notifyOfMonitorWaited(YouthMonitorWaitedEvent event) {
        monitorWaitedListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnMonitorWait(Consumer<YouthMonitorWaitEvent> listener) {
        monitorWaitListeners.add(listener);
    }

    @Override
    public void notifyOfMonitorWait(YouthMonitorWaitEvent event) {
        monitorWaitListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnMonitorContendedEntered(Consumer<YouthMonitorContendedEnteredEvent> listener) {
        monitorContendedEnteredListeners.add(listener);
    }

    @Override
    public void notifyOfMonitorContendedEntered(YouthMonitorContendedEnteredEvent event) {
        monitorContendedEnteredListeners.forEach(listener -> listener.accept(event));
    }

    @Override
    public void subscribeOnMonitorContendedEnter(Consumer<YouthMonitorContendedEnterEvent> listener) {
        monitorContendedEnterListeners.add(listener);
    }

    @Override
    public void notifyOfMonitorContendedEnter(YouthMonitorContendedEnterEvent event) {
        monitorContendedEnterListeners.forEach(listener -> listener.accept(event));
    }
}
