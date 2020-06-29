package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ClassUnloadEvent;

public interface MyClassUnloadEvent extends MyEvent, ClassUnloadEvent {
}
