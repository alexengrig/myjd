package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Method;
import com.sun.jdi.event.MethodEntryEvent;
import dev.alexengrig.myjdi.event.MyMethodEntryEvent;

public class MethodEntryEventDelegate
        extends LocatableEventDelegate<MethodEntryEvent>
        implements MyMethodEntryEvent {
    public MethodEntryEventDelegate(MethodEntryEvent event) {
        super(event);
    }

    public Method method() {
        return event.method();
    }
}
