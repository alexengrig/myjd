package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ClassPrepareEvent;
import dev.alexengrig.myjdi.event.MyClassPrepareEvent;

public class ClassPrepareEventDelegate
        extends EventDelegate<ClassPrepareEvent>
        implements MyClassPrepareEvent {
    public ClassPrepareEventDelegate(ClassPrepareEvent event) {
        super(event);
    }

    @Override
    public ThreadReference thread() {
        return event.thread();
    }

    @Override
    public ReferenceType referenceType() {
        return event.referenceType();
    }
}
