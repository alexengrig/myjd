package dev.alexengrig.myjdi.queue;

import com.sun.jdi.event.EventQueue;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

public class MyEventQueue extends YouthEventQueue.Delegate implements YouthEventQueue {
    protected final YouthVirtualMachine virtualMachine;

    public MyEventQueue(YouthVirtualMachine virtualMachine, EventQueue eventQueue) {
        super(eventQueue);
        this.virtualMachine = virtualMachine;
    }
}
