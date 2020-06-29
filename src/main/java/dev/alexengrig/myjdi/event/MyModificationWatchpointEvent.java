package dev.alexengrig.myjdi.event;

import com.sun.jdi.Value;
import com.sun.jdi.event.ModificationWatchpointEvent;
import dev.alexengrig.myjdi.event.delegate.WatchpointEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyModificationWatchpointEvent extends MyEvent, ModificationWatchpointEvent {
    static MyModificationWatchpointEvent delegate(ModificationWatchpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleModificationWatchpoint(this);
    }

    class Delegate
            extends WatchpointEventDelegate<ModificationWatchpointEvent>
            implements MyModificationWatchpointEvent {
        public Delegate(ModificationWatchpointEvent event) {
            super(event);
        }

        public Value valueToBe() {
            return event.valueToBe();
        }
    }
}
