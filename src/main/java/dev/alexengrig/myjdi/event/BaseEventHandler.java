package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.Event;

public abstract class BaseEventHandler<E extends Event> implements EventHandler<E> {
    protected final Class<E> type;

    protected BaseEventHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public Class<E> getTargetType() {
        return type;
    }
}
