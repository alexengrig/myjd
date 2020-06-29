package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ThreadStartEvent;

public interface MyThreadStartEvent extends MyEvent, ThreadStartEvent {
}
