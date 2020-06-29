package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnteredEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorContendedEnteredEvent extends MyEvent, MonitorContendedEnteredEvent {
    static MyMonitorContendedEnteredEvent delegate(MonitorContendedEnteredEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorContendedEntered(this);
    }

    class Delegate
            extends LocatableEventDelegate<MonitorContendedEnteredEvent>
            implements MyMonitorContendedEnteredEvent {
        public Delegate(MonitorContendedEnteredEvent event) {
            super(event);
        }

        @Override
        public ObjectReference monitor() {
            return event.monitor();
        }
    }
}
