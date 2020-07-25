package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.YouthBreakpointEvent;

import java.util.function.Consumer;

public class MyBreakpointHandle extends MyEventHandle<YouthBreakpointEvent> implements YouthBreakpointHandle {
    public MyBreakpointHandle(Consumer<YouthBreakpointEvent> handler) {
        super(handler);
    }
}
