package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.event.MyBreakpointEvent;

public class BreakpointEventDelegate
        extends LocatableEventDelegate<BreakpointEvent>
        implements MyBreakpointEvent {
    public BreakpointEventDelegate(BreakpointEvent event) {
        super(event);
    }
}
