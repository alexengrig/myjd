package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Location;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.LocatableEvent;
import dev.alexengrig.myjdi.event.YouthEvent;

public abstract class YouthLocatableEventDelegate<E extends LocatableEvent>
        extends YouthEvent.Delegate<E>
        implements LocatableEvent {
    public YouthLocatableEventDelegate(E event) {
        super(event);
    }

    public ThreadReference thread() {
        return event.thread();
    }

    public Location location() {
        return event.location();
    }
}
