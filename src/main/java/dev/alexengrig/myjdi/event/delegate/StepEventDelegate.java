package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.StepEvent;
import dev.alexengrig.myjdi.event.MyStepEvent;

public class StepEventDelegate
        extends LocatableEventDelegate<StepEvent>
        implements MyStepEvent {
    public StepEventDelegate(StepEvent event) {
        super(event);
    }
}
