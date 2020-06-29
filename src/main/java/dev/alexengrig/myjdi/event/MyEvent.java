package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.Event;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyEvent extends Event {
    void accept(MyEventHandler handler);
}
