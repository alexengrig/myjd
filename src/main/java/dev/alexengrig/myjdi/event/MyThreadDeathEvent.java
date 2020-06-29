package dev.alexengrig.myjdi.event;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ThreadDeathEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyThreadDeathEvent extends MyEvent, ThreadDeathEvent {
    static MyThreadDeathEvent delegate(ThreadDeathEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleThreadDeath(this);
    }

    class Delegate
            extends MyEvent.Delegate<ThreadDeathEvent>
            implements MyThreadDeathEvent {
        public Delegate(ThreadDeathEvent event) {
            super(event);
        }

        @Override
        public ThreadReference thread() {
            return event.thread();
        }
    }
}
