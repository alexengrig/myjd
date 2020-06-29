package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDisconnectEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyVMDisconnectEvent extends MyEvent, VMDisconnectEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmDisconnect(this);
    }
}
