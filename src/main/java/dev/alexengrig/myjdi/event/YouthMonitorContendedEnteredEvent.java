package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnteredEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthMonitorContendedEnteredEvent extends YouthEvent, MonitorContendedEnteredEvent {
    static YouthMonitorContendedEnteredEvent delegate(MonitorContendedEnteredEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleMonitorContendedEntered(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<MonitorContendedEnteredEvent>
            implements YouthMonitorContendedEnteredEvent {
        public Delegate(MonitorContendedEnteredEvent event) {
            super(event);
        }

        @Override
        public ObjectReference monitor() {
            return event.monitor();
        }
    }
}
