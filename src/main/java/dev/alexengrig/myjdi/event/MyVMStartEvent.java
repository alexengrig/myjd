package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMStartEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyVMStartEvent extends MyEvent, VMStartEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmStart(this);
    }
}
