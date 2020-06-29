package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorWaitEvent;
import dev.alexengrig.myjdi.event.MyMonitorWaitEvent;

public class MonitorWaitEventDelegate
        extends LocatableEventDelegate<MonitorWaitEvent>
        implements MyMonitorWaitEvent {
    public MonitorWaitEventDelegate(MonitorWaitEvent event) {
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
