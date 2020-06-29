package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Location;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.LocatableEvent;

public abstract class LocatableEventDelegate<E extends LocatableEvent>
        extends EventDelegate<E>
        implements LocatableEvent {
    public LocatableEventDelegate(E event) {
        super(event);
    }

    public ThreadReference thread() {
        return event.thread();
    }

    public Location location() {
        return event.location();
    }
}
