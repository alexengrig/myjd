package dev.alexengrig.myjdi.event;

import com.sun.jdi.Method;
import com.sun.jdi.Value;
import com.sun.jdi.event.MethodExitEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthMethodExitEvent extends YouthEvent, MethodExitEvent {
    static YouthMethodExitEvent delegate(MethodExitEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
        handler.handleMethodExit(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<MethodExitEvent>
            implements YouthMethodExitEvent {
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
