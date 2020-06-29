package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MethodEntryEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMethodEntryEvent extends MyEvent, MethodEntryEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMethodEntry(this);
    }
}
