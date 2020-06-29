package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.AccessWatchpointEvent;
import dev.alexengrig.myjdi.event.delegate.WatchpointEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyAccessWatchpointEvent extends MyEvent, AccessWatchpointEvent {
    static MyAccessWatchpointEvent delegate(AccessWatchpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleAccessWatchpoint(this);
    }

    class Delegate
            extends WatchpointEventDelegate<AccessWatchpointEvent>
            implements MyAccessWatchpointEvent {
        public Delegate(AccessWatchpointEvent event) {
            super(event);
        }
    }
}
