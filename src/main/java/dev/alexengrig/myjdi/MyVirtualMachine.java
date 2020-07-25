package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachine;
import dev.alexengrig.myjdi.handle.MyEventHandler;
import dev.alexengrig.myjdi.handle.YouthEventHandler;
import dev.alexengrig.myjdi.queue.MyEventQueue;
import dev.alexengrig.myjdi.queue.YouthEventQueue;
import dev.alexengrig.myjdi.request.MyEventRequestManager;
import dev.alexengrig.myjdi.request.YouthEventRequestManager;
import dev.alexengrig.myjdi.subscription.MyEventSubscriptionManager;
import dev.alexengrig.myjdi.subscription.YouthEventSubscriptionManager;

public class MyVirtualMachine extends YouthVirtualMachine.Delegate implements YouthVirtualMachine {
    protected YouthEventHandler eventHandler;
    protected YouthEventSubscriptionManager eventSubscriptionManager;

    public MyVirtualMachine(VirtualMachine virtualMachine) {
        super(virtualMachine);
        this.eventHandler = createEventHandler(this);
        this.eventSubscriptionManager = createEventSubscriptionManager(this);
    }

    @Override
    protected YouthEventQueue createEventQueue(VirtualMachine virtualMachine) {
        return new MyEventQueue(this, virtualMachine.eventQueue());
    }

    @Override
    protected YouthEventRequestManager createEventRequestManager(VirtualMachine virtualMachine) {
        return new MyEventRequestManager(this, virtualMachine.eventRequestManager());
    }

    protected YouthEventHandler createEventHandler(YouthVirtualMachine virtualMachine) {
        return new MyEventHandler(virtualMachine);
    }

    protected YouthEventSubscriptionManager createEventSubscriptionManager(YouthVirtualMachine virtualMachine) {
        return new MyEventSubscriptionManager(virtualMachine);
    }

    @Override
    public YouthEventHandler eventHandler() {
        return eventHandler;
    }

    @Override
    public YouthEventSubscriptionManager eventSubscriptionManager() {
        return eventSubscriptionManager;
    }
}
