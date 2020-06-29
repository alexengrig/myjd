package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorWaitedEvent;
import dev.alexengrig.myjdi.event.MyMonitorWaitedEvent;

public class MonitorWaitedEventDelegate
        extends LocatableEventDelegate<MonitorWaitedEvent>
        implements MyMonitorWaitedEvent {
    public MonitorWaitedEventDelegate(MonitorWaitedEvent event) {
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
