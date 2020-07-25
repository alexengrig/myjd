package dev.alexengrig.myjdi.queue;

import dev.alexengrig.myjdi.YouthVirtualMachine;

public class MyEventQueue extends YouthEventQueue.Delegate implements YouthEventQueue {
    public MyEventQueue(YouthVirtualMachine virtualMachine) {
        super(virtualMachine.eventQueue());
    }
}
