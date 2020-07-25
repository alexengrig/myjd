package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.YouthVirtualMachine;

public class MyEventHandler extends OmitEventHandler implements YouthEventHandler {
    protected final YouthVirtualMachine virtualMachine;

    public MyEventHandler(YouthVirtualMachine virtualMachine) {

        this.virtualMachine = virtualMachine;
    }
}
