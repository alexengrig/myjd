package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorWaitedEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthMonitorWaitedEvent extends YouthEvent, MonitorWaitedEvent {
    static YouthMonitorWaitedEvent delegate(MonitorWaitedEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMonitorWaited(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<MonitorWaitedEvent>
            implements YouthMonitorWaitedEvent {
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
