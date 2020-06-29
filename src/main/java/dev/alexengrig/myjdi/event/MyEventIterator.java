package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;

import java.util.Objects;
import java.util.function.Consumer;

public interface MyEventIterator extends EventIterator {
    static MyEventIterator delegate(EventIterator event) {
        return new Delegate(event);
    }

    @Override
    MyEvent nextEvent();

    @Override
    MyEvent next();

    class Delegate implements MyEventIterator {
        protected final EventIterator iterator;

        public Delegate(EventIterator iterator) {
            this.iterator = Objects.requireNonNull(iterator, "The iterator must not be null");
        }

        @Override
        public MyEvent nextEvent() {
            return MyEvent.findOut(iterator.nextEvent());
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
}
