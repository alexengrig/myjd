package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MonitorWaitEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorWaitEvent extends MyEvent, MonitorWaitEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorWait(this);
    }
}
