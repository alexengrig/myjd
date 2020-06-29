package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ModificationWatchpointEvent;

public interface MyModificationWatchpointEvent extends MyEvent, ModificationWatchpointEvent {
}
