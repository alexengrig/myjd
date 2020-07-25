package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.BreakpointEvent;
import dev.alexengrig.myjdi.event.YouthBreakpointEvent;

public interface YouthBreakpointHandle extends YouthEventHandle<YouthBreakpointEvent> {
    @Override
    default Class<? super BreakpointEvent> getTargetType() {
        return BreakpointEvent.class;
    }
}
