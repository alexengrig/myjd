package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ModificationWatchpointEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyModificationWatchpointEvent extends MyEvent, ModificationWatchpointEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleModificationWatchpoint(this);
    }
}
