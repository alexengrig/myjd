package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDeathEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyVMDeathEvent extends MyEvent, VMDeathEvent {
    static MyVMDeathEvent delegate(VMDeathEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmDeath(this);
    }

    class Delegate
            extends MyEvent.Delegate<VMDeathEvent>
            implements MyVMDeathEvent {
        public Delegate(VMDeathEvent event) {
            super(event);
        }
    }
}
