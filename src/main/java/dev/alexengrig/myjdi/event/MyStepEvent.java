package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.StepEvent;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyStepEvent extends MyEvent, StepEvent {
    @Override
    default void accept(MyEventHandler handler) {
        handler.handleStep(this);
    }
}
