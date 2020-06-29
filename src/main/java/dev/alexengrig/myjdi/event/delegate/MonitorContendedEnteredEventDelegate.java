package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MonitorContendedEnteredEvent;
import dev.alexengrig.myjdi.event.MyMonitorContendedEnteredEvent;

public class MonitorContendedEnteredEventDelegate
        extends LocatableEventDelegate<MonitorContendedEnteredEvent>
        implements MyMonitorContendedEnteredEvent {
    public MonitorContendedEnteredEventDelegate(MonitorContendedEnteredEvent event) {
        super(event);
    }

    @Override
    public ObjectReference monitor() {
        return event.monitor();
    }
}
