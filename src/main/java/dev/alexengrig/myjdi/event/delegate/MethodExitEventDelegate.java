package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.Method;
import com.sun.jdi.Value;
import com.sun.jdi.event.MethodExitEvent;
import dev.alexengrig.myjdi.event.MyMethodExitEvent;

public class MethodExitEventDelegate
        extends LocatableEventDelegate<MethodExitEvent>
        implements MyMethodExitEvent {
    public MethodExitEventDelegate(MethodExitEvent event) {
        super(event);
    }

    public Method method() {
        return event.method();
    }

    public Value returnValue() {
        return event.returnValue();
    }
}
