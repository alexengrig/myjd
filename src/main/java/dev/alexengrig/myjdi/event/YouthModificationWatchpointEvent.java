package dev.alexengrig.myjdi.event;

import com.sun.jdi.Value;
import com.sun.jdi.event.ModificationWatchpointEvent;
import dev.alexengrig.myjdi.event.delegate.YouthWatchpointEventDelegate;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthModificationWatchpointEvent extends YouthEvent, ModificationWatchpointEvent {
    static YouthModificationWatchpointEvent delegate(ModificationWatchpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleModificationWatchpoint(this);
    }

    class Delegate
            extends YouthWatchpointEventDelegate<ModificationWatchpointEvent>
            implements YouthModificationWatchpointEvent {
        public Delegate(ModificationWatchpointEvent event) {
            super(event);
        }

        public Value valueToBe() {
            return event.valueToBe();
        }
    }
}
