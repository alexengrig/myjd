package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorWaitedEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorWaitedEvent extends MyEvent, MonitorWaitedEvent {
    static MyMonitorWaitedEvent delegate(MonitorWaitedEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorWaited(this);
    }

    class Delegate
            extends LocatableEventDelegate<MonitorWaitedEvent>
            implements MyMonitorWaitedEvent {
        public Delegate(MonitorWaitedEvent event) {
            super(event);
        }

        @Override
        public ObjectReference monitor() {
            return event.monitor();
        }

        @Override
        public boolean timedout() {
            return event.timedout();
        }
    }
}
