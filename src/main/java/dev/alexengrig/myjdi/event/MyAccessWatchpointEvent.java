package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.AccessWatchpointEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyAccessWatchpointEvent extends MyEvent, AccessWatchpointEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleAccessWatchpoint(this);
    }
}
