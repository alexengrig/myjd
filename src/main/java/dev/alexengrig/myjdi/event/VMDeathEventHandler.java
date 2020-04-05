package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDeathEvent;

public abstract class VMDeathEventHandler extends BaseEventHandler<VMDeathEvent> {
    public VMDeathEventHandler() {
        super(VMDeathEvent.class);
    }
}
