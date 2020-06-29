package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ThreadDeathEvent;
import dev.alexengrig.myjdi.event.MyThreadDeathEvent;

public class ThreadDeathEventDelegate
        extends EventDelegate<ThreadDeathEvent>
        implements MyThreadDeathEvent {
    public ThreadDeathEventDelegate(ThreadDeathEvent event) {
        super(event);
    }

    @Override
    public ThreadReference thread() {
        return event.thread();
    }
}
