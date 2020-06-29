package dev.alexengrig.myjdi.event;

import com.sun.jdi.Method;
import com.sun.jdi.event.MethodEntryEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMethodEntryEvent extends MyEvent, MethodEntryEvent {
    static MyMethodEntryEvent delegate(MethodEntryEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMethodEntry(this);
    }

    class Delegate
            extends LocatableEventDelegate<MethodEntryEvent>
            implements MyMethodEntryEvent {
        public Delegate(MethodEntryEvent event) {
            super(event);
        }

        public Method method() {
            return event.method();
        }
    }
}
