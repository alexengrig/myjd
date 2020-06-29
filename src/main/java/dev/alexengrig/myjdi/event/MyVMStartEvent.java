package dev.alexengrig.myjdi.event;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.VMStartEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyVMStartEvent extends MyEvent, VMStartEvent {
    static MyVMStartEvent delegate(VMStartEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmStart(this);
    }

    class Delegate
            extends MyEvent.Delegate<VMStartEvent>
            implements MyVMStartEvent {
        public Delegate(VMStartEvent event) {
            super(event);
        }

        @Override
        public ThreadReference thread() {
            return event.thread();
        }
    }
}
