package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.AccessWatchpointEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthAccessWatchpointEvent extends YouthEvent, AccessWatchpointEvent {
    static YouthAccessWatchpointEvent delegate(AccessWatchpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
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
