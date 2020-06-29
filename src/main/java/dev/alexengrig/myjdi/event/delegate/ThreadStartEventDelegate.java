package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ThreadStartEvent;
import dev.alexengrig.myjdi.event.MyThreadStartEvent;

public class ThreadStartEventDelegate
        extends EventDelegate<ThreadStartEvent>
        implements MyThreadStartEvent {
    public ThreadStartEventDelegate(ThreadStartEvent event) {
        super(event);
    }

    @Override
    public ThreadReference thread() {
        return event.thread();
    }
}
