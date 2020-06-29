package dev.alexengrig.myjdi.event;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ClassPrepareEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyClassPrepareEvent extends MyEvent, ClassPrepareEvent {
    static MyClassPrepareEvent delegate(ClassPrepareEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleClassPrepare(this);
    }

    class Delegate
            extends MyEvent.Delegate<ClassPrepareEvent>
            implements MyClassPrepareEvent {
        public Delegate(ClassPrepareEvent event) {
            super(event);
        }

        @Override
        public ThreadReference thread() {
            return event.thread();
        }

        @Override
        public ReferenceType referenceType() {
            return event.referenceType();
        }
    }
}
