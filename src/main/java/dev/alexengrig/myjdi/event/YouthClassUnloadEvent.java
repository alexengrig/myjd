package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.ClassUnloadEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthClassUnloadEvent extends YouthEvent, ClassUnloadEvent {
    static YouthClassUnloadEvent delegate(ClassUnloadEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleClassUnload(this);
    }

    class Delegate
            extends YouthEvent.Delegate<ClassUnloadEvent>
            implements YouthClassUnloadEvent {
        public Delegate(ClassUnloadEvent event) {
            super(event);
        }

        @Override
        public String className() {
            return event.className();
        }

        @Override
        public String classSignature() {
            return event.classSignature();
        }
    }
}
