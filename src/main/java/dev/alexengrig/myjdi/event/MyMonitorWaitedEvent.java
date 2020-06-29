package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MonitorWaitedEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorWaitedEvent extends MyEvent, MonitorWaitedEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorWaited(this);
    }
}
