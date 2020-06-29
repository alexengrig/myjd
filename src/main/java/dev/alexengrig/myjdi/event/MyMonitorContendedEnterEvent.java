package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnterEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorContendedEnterEvent extends MyEvent, MonitorContendedEnterEvent {
    static MyMonitorContendedEnterEvent delegate(MonitorContendedEnterEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorContendedEnter(this);
    }

    class Delegate
            extends LocatableEventDelegate<MonitorContendedEnterEvent>
            implements MyMonitorContendedEnterEvent {
        public Delegate(MonitorContendedEnterEvent event) {
            super(event);
        }

        @Override
        public ObjectReference monitor() {
            return event.monitor();
        }
    }
}
