package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.Event;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyEvent extends Event {
    default void accept(MyEventHandler handler) {
        handler.handle(this);
    }
}
