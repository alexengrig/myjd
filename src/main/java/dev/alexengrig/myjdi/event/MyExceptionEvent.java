package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ExceptionEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyExceptionEvent extends MyEvent, ExceptionEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleException(this);
    }
}
