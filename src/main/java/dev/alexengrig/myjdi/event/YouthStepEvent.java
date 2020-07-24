package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.StepEvent;
import dev.alexengrig.myjdi.event.delegate.YouthLocatableEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface YouthStepEvent extends YouthEvent, StepEvent {
    static YouthStepEvent delegate(StepEvent event) {
        return new Delegate(event);
    }

    @Override
    default void accept(MyEventHandler handler) {
        handler.handleStep(this);
    }

    class Delegate
            extends YouthLocatableEventDelegate<StepEvent>
            implements YouthStepEvent {
        public Delegate(StepEvent event) {
            super(event);
        }
    }
}
