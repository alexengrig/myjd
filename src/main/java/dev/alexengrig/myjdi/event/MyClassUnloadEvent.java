package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ClassUnloadEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyClassUnloadEvent extends MyEvent, ClassUnloadEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleClassUnload(this);
    }
}
