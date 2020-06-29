package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorWaitEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMonitorWaitEvent extends MyEvent, MonitorWaitEvent {
    static MyMonitorWaitEvent delegate(MonitorWaitEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorWait(this);
    }

    class Delegate
            extends LocatableEventDelegate<MonitorWaitEvent>
            implements MyMonitorWaitEvent {
        public Delegate(MonitorWaitEvent event) {
            super(event);
        }

        @Override
        public ObjectReference monitor() {
            return event.monitor();
        }

        @Override
        public long timeout() {
            return event.timeout();
        }
    }
}
