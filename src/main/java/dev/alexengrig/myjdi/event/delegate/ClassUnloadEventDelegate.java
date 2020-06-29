package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.ClassUnloadEvent;
import dev.alexengrig.myjdi.event.MyClassUnloadEvent;

public class ClassUnloadEventDelegate
        extends EventDelegate<ClassUnloadEvent>
        implements MyClassUnloadEvent {
    public ClassUnloadEventDelegate(ClassUnloadEvent event) {
        super(event);
    }

    @Override
    public String className() {
        return event.className();
    }

    @Override
    public String classSignature() {
        return event.classSignature();
    }
}
