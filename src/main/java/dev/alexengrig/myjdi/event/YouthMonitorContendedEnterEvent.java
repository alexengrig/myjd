package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnterEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthMonitorContendedEnterEvent extends YouthEvent, MonitorContendedEnterEvent {
    static YouthMonitorContendedEnterEvent delegate(MonitorContendedEnterEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
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
