package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ThreadStartEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyThreadStartEvent extends MyEvent, ThreadStartEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleThreadStart(this);
    }
}
