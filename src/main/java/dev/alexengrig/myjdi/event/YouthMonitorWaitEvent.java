package dev.alexengrig.myjdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorWaitEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthMonitorWaitEvent extends YouthEvent, MonitorWaitEvent {
    static YouthMonitorWaitEvent delegate(MonitorWaitEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleMonitorWait(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<MonitorWaitEvent>
            implements YouthMonitorWaitEvent {
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
