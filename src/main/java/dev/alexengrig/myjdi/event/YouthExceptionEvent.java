package dev.alexengrig.myjdi.event;

import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.ExceptionEvent;
import dev.alexengrig.myjdi.handle.YouthEventHandler;

public interface YouthExceptionEvent extends YouthEvent, ExceptionEvent {
    static YouthExceptionEvent delegate(ExceptionEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(YouthEventHandler handler) {
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
