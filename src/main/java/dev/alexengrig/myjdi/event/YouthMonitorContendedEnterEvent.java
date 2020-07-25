package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnterEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthMonitorContendedEnterEvent extends YouthEvent, MonitorContendedEnterEvent {
    static YouthMonitorContendedEnterEvent delegate(MonitorContendedEnterEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleMonitorContendedEnter(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<MonitorContendedEnterEvent>
            implements YouthMonitorContendedEnterEvent {
        public Delegate(MonitorContendedEnterEvent event) {
            super(event);
        }

        @Override
        public ObjectReference monitor() {
            return event.monitor();
        }
    }
}
