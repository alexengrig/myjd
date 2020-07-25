package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.YouthVirtualMachine;

public class MyEventSubscriptionManager implements YouthEventSubscriptionManager {
    protected final YouthVirtualMachine virtualMachine;

    public MyEventSubscriptionManager(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    @Override
    public YouthEventSubscription subscribe() {
        throw new UnsupportedOperationException();
    }
}
