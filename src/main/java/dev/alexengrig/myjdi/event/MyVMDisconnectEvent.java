package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDisconnectEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyVMDisconnectEvent extends MyEvent, VMDisconnectEvent {
    static MyVMDisconnectEvent delegate(VMDisconnectEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmDisconnect(this);
    }

    class Delegate
            extends MyEvent.Delegate<VMDisconnectEvent>
            implements MyVMDisconnectEvent {
        public Delegate(VMDisconnectEvent event) {
            super(event);
        }
    }
}
