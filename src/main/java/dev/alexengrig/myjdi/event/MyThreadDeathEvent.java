package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ThreadDeathEvent;

public interface MyThreadDeathEvent extends MyEvent, ThreadDeathEvent {
}
