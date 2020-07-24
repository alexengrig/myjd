package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.sun.jdi.event.WatchpointEvent;

public abstract class YouthWatchpointEventDelegate<E extends WatchpointEvent>
        extends YouthLocatableEventDelegate<E>
        implements WatchpointEvent {
    public YouthWatchpointEventDelegate(E event) {
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
