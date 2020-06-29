package dev.alexengrig.myjdi.event.delegate;

import com.sun.jdi.event.VMDeathEvent;
import dev.alexengrig.myjdi.event.MyVMDeathEvent;

public class VmDeathEventDelegate
        extends EventDelegate<VMDeathEvent>
        implements MyVMDeathEvent {
    public VmDeathEventDelegate(VMDeathEvent event) {
        super(event);
    }
}
