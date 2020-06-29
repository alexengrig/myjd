package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ExceptionEvent;

public interface MyExceptionEvent extends MyEvent, ExceptionEvent {
}
