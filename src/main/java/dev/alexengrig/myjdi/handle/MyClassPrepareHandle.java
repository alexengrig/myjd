package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.event.YouthClassPrepareEvent;

import java.util.function.Consumer;

public class MyClassPrepareHandle extends MyEventHandle<YouthClassPrepareEvent> implements YouthClassPrepareHandle {
    public MyClassPrepareHandle(Consumer<YouthClassPrepareEvent> handler) {
        super(handler);
    }
}
