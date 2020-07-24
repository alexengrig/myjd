package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDisconnectEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthVMDisconnectEvent extends YouthEvent, VMDisconnectEvent {
    static YouthVMDisconnectEvent delegate(VMDisconnectEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleVmDisconnect(this);
    }

    class Delegate
            extends YouthEvent.Delegate<VMDisconnectEvent>
            implements YouthVMDisconnectEvent {
        public Delegate(VMDisconnectEvent event) {
            super(event);
        }
    }
}
