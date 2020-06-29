package dev.alexengrig.myjdi.event;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ThreadStartEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyThreadStartEvent extends MyEvent, ThreadStartEvent {
    static MyThreadStartEvent delegate(ThreadStartEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleThreadStart(this);
    }

    class Delegate
            extends MyEvent.Delegate<ThreadStartEvent>
            implements MyThreadStartEvent {
        public Delegate(ThreadStartEvent event) {
            super(event);
        }

        @Override
        public ThreadReference thread() {
            return event.thread();
        }
    }
}
