package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyBreakpointEvent extends MyEvent, BreakpointEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleBreakpoint(this);
    }
}
