package dev.alexengrig.myjdi.event;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;

public abstract class EventQueueDelegate implements MyEventQueue {
    protected final EventQueue queue;

    protected EventQueueDelegate(EventQueue queue) {
        this.queue = queue;
    }

    @Override
    public EventSet remove() throws InterruptedException {
        return queue.remove();
    }

    @Override
    public EventSet remove(long timeout) throws InterruptedException {
        return queue.remove(timeout);
    }

    @Override
    public VirtualMachine virtualMachine() {
        return queue.virtualMachine();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
