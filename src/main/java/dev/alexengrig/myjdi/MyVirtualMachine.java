package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachine;
import dev.alexengrig.myjdi.handle.MyEventHandleManager;
import dev.alexengrig.myjdi.handle.YouthEventHandleManager;
import dev.alexengrig.myjdi.request.MyEventRequestManager;
import dev.alexengrig.myjdi.request.YouthEventRequestManager;

public class MyVirtualMachine extends YouthVirtualMachine.Delegate implements YouthVirtualMachine {
    protected final YouthEventRequestManager requestManager;
    protected final YouthEventHandleManager handleManager;

    public MyVirtualMachine(VirtualMachine virtualMachine) {
        this(virtualMachine,
                new MyEventRequestManager(virtualMachine.eventRequestManager()),
                new MyEventHandleManager());
    }

    protected MyVirtualMachine(VirtualMachine virtualMachine,
                               YouthEventRequestManager requestManager, YouthEventHandleManager handleManager) {
        super(virtualMachine);
        this.requestManager = requestManager;
        this.handleManager = handleManager;
    }

    @Override
    public YouthEventRequestManager eventRequestManager() {
        return requestManager;
    }

    @Override
    public YouthEventHandleManager eventHandleManager() {
        return handleManager;
    }
}
