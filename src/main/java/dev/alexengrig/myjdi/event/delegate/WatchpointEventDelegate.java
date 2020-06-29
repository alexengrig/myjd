package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.sun.jdi.event.WatchpointEvent;

public abstract class WatchpointEventDelegate<E extends WatchpointEvent>
        extends LocatableEventDelegate<E>
        implements WatchpointEvent {
    public WatchpointEventDelegate(E event) {
        super(event);
    }

    public Field field() {
        return event.field();
    }

    public ObjectReference object() {
        return event.object();
    }

    public Value valueCurrent() {
        return event.valueCurrent();
    }
}
