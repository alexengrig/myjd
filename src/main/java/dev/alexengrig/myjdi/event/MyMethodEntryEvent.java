package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.MethodEntryEvent;

public interface MyMethodEntryEvent extends MyEvent, MethodEntryEvent {
}
