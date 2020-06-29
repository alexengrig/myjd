package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyBreakpointEvent extends MyEvent, BreakpointEvent {
    static MyBreakpointEvent delegate(BreakpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleBreakpoint(this);
    }

    class Delegate
            extends LocatableEventDelegate<BreakpointEvent>
            implements MyBreakpointEvent {
        public Delegate(BreakpointEvent event) {
            super(event);
        }
    }
}
