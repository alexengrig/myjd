package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.AccessWatchpointEvent;
import dev.alexengrig.myjdi.event.MyAccessWatchpointEvent;

public class AccessWatchpointEventDelegate
        extends WatchpointEventDelegate<AccessWatchpointEvent>
        implements MyAccessWatchpointEvent {
    public AccessWatchpointEventDelegate(AccessWatchpointEvent event) {
        super(event);
    }
}
