package dev.alexengrig.myjdi.event;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ClassPrepareEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthClassPrepareEvent extends YouthEvent, ClassPrepareEvent {
    static YouthClassPrepareEvent delegate(ClassPrepareEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleClassPrepare(this);
    }

    class Delegate
            extends YouthEvent.Delegate<ClassPrepareEvent>
            implements YouthClassPrepareEvent {
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
