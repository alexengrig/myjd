package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MonitorContendedEnterEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorContendedEnterEvent extends MyEvent, MonitorContendedEnterEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorContendedEnter(this);
    }
}
