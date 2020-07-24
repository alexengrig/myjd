package dev.alexengrig.myjdi.event;

import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.ExceptionEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthExceptionEvent extends YouthEvent, ExceptionEvent {
    static YouthExceptionEvent delegate(ExceptionEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleException(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<ExceptionEvent>
            implements YouthExceptionEvent {
        public Delegate(ExceptionEvent event) {
            super(event);
        }

        public ObjectReference exception() {
            return event.exception();
        }

        public Location catchLocation() {
            return event.catchLocation();
        }
    }
}
