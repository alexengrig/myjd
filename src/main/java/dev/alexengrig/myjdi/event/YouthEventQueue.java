package dev.alexengrig.myjdi.event;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;

public interface YouthEventQueue extends EventQueue {
    static YouthEventQueue delegate(EventQueue event) {
        return new Delegate(event);
    }

    @Override
    YouthEventSet remove() throws InterruptedException;

    @Override
    YouthEventSet remove(long timeout) throws InterruptedException;

    class Delegate implements YouthEventQueue {
        protected final EventQueue queue;

        public Delegate(EventQueue queue) {
            this.queue = queue;
        }

        protected YouthEventSet wrap(EventSet set) {
            return new YouthEventSet.Delegate(set);
        }

        @Override
        public YouthEventSet remove() throws InterruptedException {
            return wrap(queue.remove());
        }

        @Override
        public YouthEventSet remove(long timeout) throws InterruptedException {
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
}
