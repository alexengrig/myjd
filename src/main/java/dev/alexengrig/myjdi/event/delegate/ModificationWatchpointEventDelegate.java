package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Value;
import com.sun.jdi.event.ModificationWatchpointEvent;
import dev.alexengrig.myjdi.event.MyModificationWatchpointEvent;

public class ModificationWatchpointEventDelegate
        extends WatchpointEventDelegate<ModificationWatchpointEvent>
        implements MyModificationWatchpointEvent {
    public ModificationWatchpointEventDelegate(ModificationWatchpointEvent event) {
        super(event);
    }

    public Value valueToBe() {
        return event.valueToBe();
    }
}
