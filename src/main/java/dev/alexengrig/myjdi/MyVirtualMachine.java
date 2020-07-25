package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachine;
import dev.alexengrig.myjdi.handle.YouthEventHandleManager;
import dev.alexengrig.myjdi.queue.MyEventQueue;
import dev.alexengrig.myjdi.queue.YouthEventQueue;
import dev.alexengrig.myjdi.request.MyEventRequestManager;
import dev.alexengrig.myjdi.request.YouthEventRequestManager;
import dev.alexengrig.myjdi.subscription.YouthEventSubscriptionManager;

public class MyVirtualMachine extends YouthVirtualMachine.Delegate implements YouthVirtualMachine {
    protected YouthEventHandleManager handleManager;
    protected YouthEventSubscriptionManager subscriptionManager;

    public MyVirtualMachine(VirtualMachine virtualMachine) {
        super(virtualMachine);
    }

    @Override
    protected YouthEventQueue createEventQueue(VirtualMachine virtualMachine) {
        return new MyEventQueue(this);
    }

    @Override
    protected YouthEventRequestManager createEventRequestManager(VirtualMachine virtualMachine) {
        return new MyEventRequestManager(this);
    }

    @Override
    public YouthEventHandleManager eventHandleManager() {
        return handleManager;
    }

    @Override
    public YouthEventSubscriptionManager eventSubscriptionManager() {
        return subscriptionManager;
    }
}
