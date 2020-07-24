package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.ClassPrepareEvent;

import java.util.function.Consumer;

public class MyClassPrepareHandle extends MyEventHandle<ClassPrepareEvent> implements YouthClassPrepareHandle {
    public MyClassPrepareHandle(Consumer<ClassPrepareEvent> handler) {
        super(handler);
    }
}
