package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.YouthVirtualMachine;
import dev.alexengrig.myjdi.event.YouthBreakpointEvent;
import dev.alexengrig.myjdi.event.YouthClassPrepareEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyEventSubscriptionManager implements YouthEventSubscriptionManager {
    protected final YouthVirtualMachine virtualMachine;
    protected final List<Consumer<YouthClassPrepareEvent>> classPrepareListeners;
    protected final List<Consumer<YouthBreakpointEvent>> breakpointListeners;

    public MyEventSubscriptionManager(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        classPrepareListeners = new ArrayList<>();
        breakpointListeners = new ArrayList<>();
    }

    @Override
    public YouthEventSubscription subscribe() {
        throw new UnsupportedOperationException();
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
    public void subscribeOnBreakpoint(Consumer<YouthBreakpointEvent> listener) {
        breakpointListeners.add(listener);
    }

    @Override
    public void notifyOfBreakpoint(YouthBreakpointEvent event) {
        breakpointListeners.forEach(listener -> listener.accept(event));
    }
}
