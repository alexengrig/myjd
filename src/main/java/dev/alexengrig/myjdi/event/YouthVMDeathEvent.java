package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDeathEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthVMDeathEvent extends YouthEvent, VMDeathEvent {
    static YouthVMDeathEvent delegate(VMDeathEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmDeath(this);
    }

    class Delegate
            extends YouthEvent.Delegate<VMDeathEvent>
            implements YouthVMDeathEvent {
        public Delegate(VMDeathEvent event) {
            super(event);
        }
    }
}
