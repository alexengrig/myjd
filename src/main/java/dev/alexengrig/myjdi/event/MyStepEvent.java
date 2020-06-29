package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.StepEvent;
import dev.alexengrig.myjdi.event.delegate.LocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyStepEvent extends MyEvent, StepEvent {
    static MyStepEvent delegate(StepEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleStep(this);
    }

    class Delegate
            extends LocatableEventDelegate<StepEvent>
            implements MyStepEvent {
        public Delegate(StepEvent event) {
            super(event);
        }
    }
}
