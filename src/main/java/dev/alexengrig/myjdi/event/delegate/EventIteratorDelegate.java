package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import dev.alexengrig.myjdi.event.MyEvent;
import dev.alexengrig.myjdi.event.MyEventIterator;

import java.util.Objects;
import java.util.function.Consumer;

public class EventIteratorDelegate implements MyEventIterator {
    protected final EventIterator iterator;

    public EventIteratorDelegate(EventIterator iterator) {
        this.iterator = Objects.requireNonNull(iterator, "The iterator must not be null");
    }

    @Override
    public MyEvent nextEvent() {
        return MyEvent.delegate(iterator.nextEvent());
    }

    @Override
    public MyEvent next() {
        return nextEvent();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super Event> action) {
        iterator.forEachRemaining(action);
    }
}
