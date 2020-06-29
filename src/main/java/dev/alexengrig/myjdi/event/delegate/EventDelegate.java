package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.EventRequest;
import dev.alexengrig.myjdi.event.MyEvent;

import java.util.Objects;

public class EventDelegate<E extends Event> implements MyEvent {
    protected final E event;

    public EventDelegate(E event) {
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
