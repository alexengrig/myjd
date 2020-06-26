package dev.alexengrig.myjdi.event;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.EventRequest;

import java.util.Objects;

public abstract class EventDelegate<T extends Event> implements MyEvent {
    protected final T event;

    protected EventDelegate(T event) {
        this.event = Objects.requireNonNull(event, "The event must not be null");
    }

    @Override
    public EventRequest request() {
        return event.request();
    }

    @Override
    public VirtualMachine virtualMachine() {
        return event.virtualMachine();
    }

    @Override
    public String toString() {
        return event.toString();
    }
}
