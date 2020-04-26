package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ClassPrepareEvent;

public abstract class ClassPrepareEventHandler extends BaseEventHandler<ClassPrepareEvent> {
    protected ClassPrepareEventHandler() {
        super(ClassPrepareEvent.class);
    }
}
