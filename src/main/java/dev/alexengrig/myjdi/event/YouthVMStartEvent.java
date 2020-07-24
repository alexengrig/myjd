package dev.alexengrig.myjdi.event;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.VMStartEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthVMStartEvent extends YouthEvent, VMStartEvent {
    static YouthVMStartEvent delegate(VMStartEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleVmStart(this);
    }

    class Delegate
            extends YouthEvent.Delegate<VMStartEvent>
            implements YouthVMStartEvent {
        public Delegate(VMStartEvent event) {
            super(event);
        }

        @Override
        public ThreadReference thread() {
            return event.thread();
        }
    }
}
