package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthBreakpointEvent extends YouthEvent, BreakpointEvent {
    static YouthBreakpointEvent delegate(BreakpointEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleBreakpoint(this);
    }

    class Delegate extends YouthLocatableEventDelegate<BreakpointEvent> implements YouthBreakpointEvent {
        public Delegate(BreakpointEvent event) {
            super(event);
        }
    }
}
