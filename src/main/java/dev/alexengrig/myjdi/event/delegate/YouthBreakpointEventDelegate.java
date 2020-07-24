package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.event.YouthBreakpointEvent;

public class YouthBreakpointEventDelegate
        extends YouthLocatableEventDelegate<BreakpointEvent>
        implements YouthBreakpointEvent {
    public YouthBreakpointEventDelegate(BreakpointEvent event) {
        super(event);
    }
}
