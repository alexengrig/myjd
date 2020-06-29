package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.AccessWatchpointEvent;

public interface MyAccessWatchpointEvent extends MyEvent, AccessWatchpointEvent {
}
