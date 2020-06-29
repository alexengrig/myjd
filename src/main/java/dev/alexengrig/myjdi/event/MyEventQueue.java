package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.EventQueue;

public interface MyEventQueue extends EventQueue {
    @Override
    MyEventSet remove() throws InterruptedException;

    @Override
    MyEventSet remove(long timeout) throws InterruptedException;
}
