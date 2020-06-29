package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.EventIterator;

public interface MyEventIterator extends EventIterator {
    @Override
    MyEvent nextEvent();

    @Override
    MyEvent next();
}
