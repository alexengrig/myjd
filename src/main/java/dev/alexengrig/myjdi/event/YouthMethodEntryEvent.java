package dev.alexengrig.myjdi.event;

import com.sun.jdi.Method;
import com.sun.jdi.event.MethodEntryEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthMethodEntryEvent extends YouthEvent, MethodEntryEvent {
    static YouthMethodEntryEvent delegate(MethodEntryEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleMethodEntry(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<MethodEntryEvent>
            implements YouthMethodEntryEvent {
        public Delegate(MethodEntryEvent event) {
            super(event);
        }

        public Method method() {
            return event.method();
        }
    }
}
