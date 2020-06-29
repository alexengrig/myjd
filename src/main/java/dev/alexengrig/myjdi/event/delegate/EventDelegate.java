package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import dev.alexengrig.myjdi.event.MyEvent;
import dev.alexengrig.myjdi.request.MyEventRequest;

import java.util.Objects;

public class EventDelegate<E extends Event> implements MyEvent {
    protected final E event;

    public EventDelegate(E event) {
        this.event = Objects.requireNonNull(event, "The event must not be null");
    }

    @Override
    public MyEventRequest request() {
        return MyEventRequest.delegate(event.request());
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
