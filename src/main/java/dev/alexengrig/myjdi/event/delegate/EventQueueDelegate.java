package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import dev.alexengrig.myjdi.event.MyEventQueue;
import dev.alexengrig.myjdi.event.MyEventSet;

public abstract class EventQueueDelegate implements MyEventQueue {
    protected final EventQueue queue;

    public EventQueueDelegate(EventQueue queue) {
        this.queue = queue;
    }

    protected MyEventSet wrap(EventSet set) {
        return new EventSetDelegate(set);
    }

    @Override
    public MyEventSet remove() throws InterruptedException {
        return wrap(queue.remove());
    }

    @Override
    public MyEventSet remove(long timeout) throws InterruptedException {
        return wrap(queue.remove(timeout));
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
