package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.AccessWatchpointEvent;
import dev.alexengrig.myjdi.event.delegate.YouthWatchpointEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthAccessWatchpointEvent extends YouthEvent, AccessWatchpointEvent {
    static YouthAccessWatchpointEvent delegate(AccessWatchpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleAccessWatchpoint(this);
    }

    class Delegate
            extends YouthWatchpointEventDelegate<AccessWatchpointEvent>
            implements YouthAccessWatchpointEvent {
        public Delegate(AccessWatchpointEvent event) {
            super(event);
        }
    }
}
