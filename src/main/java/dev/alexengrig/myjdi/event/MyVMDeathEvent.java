package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDeathEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyVMDeathEvent extends MyEvent, VMDeathEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmDeath(this);
    }
}
