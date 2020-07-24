package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthBreakpointEvent extends YouthEvent, BreakpointEvent {
    static YouthBreakpointEvent delegate(BreakpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleBreakpoint(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<BreakpointEvent>
            implements YouthBreakpointEvent {
        public Delegate(BreakpointEvent event) {
            super(event);
        }
    }
}
