package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ClassPrepareEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyClassPrepareEvent extends MyEvent, ClassPrepareEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleClassPrepare(this);
    }
}
