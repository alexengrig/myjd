package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.YouthVirtualMachine;
import dev.alexengrig.myjdi.event.YouthBreakpointEvent;
import dev.alexengrig.myjdi.event.YouthClassPrepareEvent;
import dev.alexengrig.myjdi.event.YouthExceptionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class MyEventHandleManager implements YouthEventHandleManager {
    protected final YouthVirtualMachine virtualMachine;
    protected final List<YouthClassPrepareHandle> classPrepareHandles;
    protected final List<YouthBreakpointHandle> breakpointHandles;

    public MyEventHandleManager(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        this.classPrepareHandles = new ArrayList<>();
        this.breakpointHandles = new ArrayList<>();
    }

    @Override
    public void createExceptionHandle(Consumer<YouthExceptionEvent> handler) {
        virtualMachine.eventSubscriptionManager().subscribeOnException(handler::accept);
    }

    @Override
    public YouthClassPrepareHandle createClassPrepareHandle(Consumer<YouthClassPrepareEvent> handler) {
        MyClassPrepareHandle classPrepareHandle = new MyClassPrepareHandle(handler);
        classPrepareHandles.add(classPrepareHandle);
        virtualMachine.eventSubscriptionManager().subscribeOnClassPrepare(handler::accept);
        return classPrepareHandle;
    }

    @Override
    public List<YouthClassPrepareHandle> classPrepareHandles() {
        return Collections.unmodifiableList(classPrepareHandles);
    }

    @Override
    public YouthEventHandle<YouthBreakpointEvent> createBreakpointHandle(Consumer<YouthBreakpointEvent> handler) {
        MyBreakpointHandle breakpointHandle = new MyBreakpointHandle(handler);
        breakpointHandles.add(breakpointHandle);
        virtualMachine.eventSubscriptionManager().subscribeOnBreakpoint(handler::accept);
        return breakpointHandle;
    }

    @Override
    public List<YouthBreakpointHandle> breakpointHandles() {
        return Collections.unmodifiableList(breakpointHandles);
    }
}
