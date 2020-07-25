package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.YouthBreakpointEvent;
import dev.alexengrig.myjdi.event.YouthClassPrepareEvent;
import dev.alexengrig.myjdi.event.YouthExceptionEvent;

import java.util.List;
import java.util.function.Consumer;

public interface YouthEventHandleManager {
    YouthClassPrepareHandle createClassPrepareHandle(Consumer<YouthClassPrepareEvent> handler);

    List<YouthClassPrepareHandle> classPrepareHandles();

    YouthEventHandle<YouthBreakpointEvent> createBreakpointHandle(Consumer<YouthBreakpointEvent> handler);

    List<YouthBreakpointHandle> breakpointHandles();

    void createExceptionHandle(Consumer<YouthExceptionEvent> handler);
}
