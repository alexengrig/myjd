package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.VMStartEvent;
import dev.alexengrig.myjdi.event.MyVMStartEvent;

public class VmStartEventDelegate
        extends EventDelegate<VMStartEvent>
        implements MyVMStartEvent {
    public VmStartEventDelegate(VMStartEvent event) {
        super(event);
    }

    @Override
    public ThreadReference thread() {
        return event.thread();
    }
}
