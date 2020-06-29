package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.VMDisconnectEvent;
import dev.alexengrig.myjdi.event.MyVMDisconnectEvent;

public class VmDisconnectEventDelegate
        extends EventDelegate<VMDisconnectEvent>
        implements MyVMDisconnectEvent {
    public VmDisconnectEventDelegate(VMDisconnectEvent event) {
        super(event);
    }
}
