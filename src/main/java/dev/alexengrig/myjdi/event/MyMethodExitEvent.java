package dev.alexengrig.myjdi.event;

import com.sun.jdi.Method;
import com.sun.jdi.Value;
import com.sun.jdi.event.MethodExitEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyMethodExitEvent extends MyEvent, MethodExitEvent {
    static MyMethodExitEvent delegate(MethodExitEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleMethodExit(this);
    }

    class Delegate
            extends LocatableEventDelegate<MethodExitEvent>
            implements MyMethodExitEvent {
        public Delegate(MethodExitEvent event) {
            super(event);
        }

        public Method method() {
            return event.method();
        }

        public Value returnValue() {
            return event.returnValue();
        }
    }
}
