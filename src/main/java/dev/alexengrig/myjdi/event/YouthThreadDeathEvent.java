package dev.alexengrig.myjdi.event;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ThreadDeathEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthThreadDeathEvent extends YouthEvent, ThreadDeathEvent {
    static YouthThreadDeathEvent delegate(ThreadDeathEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleThreadDeath(this);
    }

    class Delegate
            extends YouthEvent.Delegate<ThreadDeathEvent>
            implements YouthThreadDeathEvent {
        public Delegate(ThreadDeathEvent event) {
            super(event);
        }

        @Override
        public ThreadReference thread() {
            return event.thread();
        }
    }
}
