package dev.alexengrig.myjdi.request;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjdi.handle.MyEventHandleManager;
import dev.alexengrig.myjdi.handle.YouthClassPrepareHandle;
import dev.alexengrig.myjdi.handle.YouthEventHandleManager;

import java.util.List;

public class MyEventRequestManager extends YouthEventRequestManager.Delegate implements YouthEventRequestManager {
    protected final YouthEventHandleManager handleManager;

    public MyEventRequestManager(EventRequestManager requestManager) {
        this(requestManager, new MyEventHandleManager());
    }

    protected MyEventRequestManager(EventRequestManager requestManager, YouthEventHandleManager handleManager) {
        super(requestManager);
        this.handleManager = handleManager;
    }

    @Override
    public void createBreakpointRequest(String className, int line) {
        ClassPrepareRequest request = requestManager.createClassPrepareRequest();
        request.addClassFilter(className);
        request.enable();
        YouthClassPrepareHandle handle = handleManager.createClassPrepareHandle(event -> {
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
