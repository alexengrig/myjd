package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ThreadDeathEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyThreadDeathEvent extends MyEvent, ThreadDeathEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleThreadDeath(this);
    }
}
