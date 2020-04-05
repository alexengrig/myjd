package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.Event;

@FunctionalInterface
public interface EventHandler<E extends Event> {
    void handle(E event);

    @SuppressWarnings("unchecked")
    default void handleEvent(Event event) {
        if (!isTargetType(event)) {
            final Class<? extends Event> type = event.getClass();
            final Class<? super E> target = getTargetType();
            final String msg = String.format("%s is not target type: %s", type.getName(), target.getName());
            throw new IllegalArgumentException(msg);
        }
        handle((E) event);
    }

    default boolean isTargetType(Event event) {
        return getTargetType().isInstance(event);
    }

    default Class<? super E> getTargetType() {
        return Event.class;
    }
}
