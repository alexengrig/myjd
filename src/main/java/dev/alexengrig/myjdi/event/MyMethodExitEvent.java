package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MethodExitEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMethodExitEvent extends MyEvent, MethodExitEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMethodExit(this);
    }
}
