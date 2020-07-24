package dev.alexengrig.myjdi.event;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ThreadDeathEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthThreadDeathEvent extends YouthEvent, ThreadDeathEvent {
    static YouthThreadDeathEvent delegate(ThreadDeathEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
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
