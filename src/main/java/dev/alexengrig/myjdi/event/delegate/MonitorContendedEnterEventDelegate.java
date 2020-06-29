package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnterEvent;
import dev.alexengrig.myjdi.event.MyMonitorContendedEnterEvent;

public class MonitorContendedEnterEventDelegate
        extends LocatableEventDelegate<MonitorContendedEnterEvent>
        implements MyMonitorContendedEnterEvent {
    public MonitorContendedEnterEventDelegate(MonitorContendedEnterEvent event) {
        super(event);
    }

    @Override
    public ObjectReference monitor() {
        return event.monitor();
    }
}
