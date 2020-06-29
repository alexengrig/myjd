package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.ExceptionEvent;
import dev.alexengrig.myjdi.event.MyExceptionEvent;

public class ExceptionEventDelegate
        extends LocatableEventDelegate<ExceptionEvent>
        implements MyExceptionEvent {
    public ExceptionEventDelegate(ExceptionEvent event) {
        super(event);
    }

    public ObjectReference exception() {
        return event.exception();
    }

    public Location catchLocation() {
        return event.catchLocation();
    }
}
