package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ClassPrepareEvent;

public interface MyClassPrepareEvent extends MyEvent, ClassPrepareEvent {
}
