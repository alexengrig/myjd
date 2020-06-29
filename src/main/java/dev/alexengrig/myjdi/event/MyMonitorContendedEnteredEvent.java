package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MonitorContendedEnteredEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorContendedEnteredEvent extends MyEvent, MonitorContendedEnteredEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorContendedEntered(this);
    }
}
