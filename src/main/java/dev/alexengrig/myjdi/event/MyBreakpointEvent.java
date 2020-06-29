package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.BreakpointEvent;

public interface MyBreakpointEvent extends MyEvent, BreakpointEvent {
}
