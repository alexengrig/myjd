package dev.alexengrig.myjdi.request;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import dev.alexengrig.myjdi.YouthVirtualMachine;
import dev.alexengrig.myjdi.handle.YouthClassPrepareHandle;

import java.util.List;

public class MyEventRequestManager extends YouthEventRequestManager.Delegate implements YouthEventRequestManager {
    protected final YouthVirtualMachine virtualMachine;

    public MyEventRequestManager(YouthVirtualMachine virtualMachine) {
        super(virtualMachine.eventRequestManager());
        this.virtualMachine = virtualMachine;
    }

    @Override
    public void createBreakpointRequest(String className, int line) {
        ClassPrepareRequest request = requestManager.createClassPrepareRequest();
        request.addClassFilter(className);
        request.enable();
        YouthClassPrepareHandle handle = virtualMachine.eventHandleManager().createClassPrepareHandle(event -> {
            ReferenceType type = event.referenceType();
            if (className.equals(type.name())) {
                try {
                    List<Location> locations = type.locationsOfLine(type.defaultStratum(), type.sourceName(), line);
                    if (!locations.isEmpty()) {
                        Location location = locations.get(0);
                        BreakpointRequest breakpointRequest = requestManager.createBreakpointRequest(location);
                        breakpointRequest.enable();
                    }
                } catch (AbsentInformationException e) {
                    e.printStackTrace();
                }
            }
        });
        handle.enable();
    }
}
