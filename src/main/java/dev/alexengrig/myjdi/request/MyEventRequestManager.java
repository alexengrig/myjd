package dev.alexengrig.myjdi.request;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjdi.YouthVirtualMachine;
import dev.alexengrig.myjdi.handle.YouthClassPrepareHandle;

import java.util.List;

public class MyEventRequestManager extends YouthEventRequestManager.Delegate implements YouthEventRequestManager {
    protected final YouthVirtualMachine virtualMachine;

    public MyEventRequestManager(YouthVirtualMachine virtualMachine, EventRequestManager eventRequestManager) {
        super(eventRequestManager);
        this.virtualMachine = virtualMachine;
    }

    @Override
    public void createExceptionRequest(String className, boolean notifyCaught, boolean notifyUncaught) {
        YouthEventRequestManager eventRequestManager = virtualMachine.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(className);
        classPrepareRequest.enable();
        virtualMachine.eventSubscriptionManager().subscribeOnClassPrepare(event -> {
            ReferenceType referenceType = event.referenceType();
            if (referenceType.name().equals(className)) {
                eventRequestManager.createExceptionRequest(referenceType, notifyCaught, notifyUncaught).enable();
            }
        });
    }

    @Override
    public void createAllExceptionRequest(String className) {
        createExceptionRequest(className, true, true);
    }

    @Override
    public void createCaughtExceptionRequest(String className) {
        createExceptionRequest(className, true, false);
    }

    @Override
    public void createUncaughtExceptionRequest(String className) {
        createExceptionRequest(className, false, true);
    }

    @Override
    public void createBreakpointRequest(String className, int line) {
        YouthEventRequestManager eventRequestManager = virtualMachine.eventRequestManager();
        ClassPrepareRequest request = eventRequestManager.createClassPrepareRequest();
        request.addClassFilter(className);
        request.enable();
        YouthClassPrepareHandle handle = virtualMachine.eventHandleManager().createClassPrepareHandle(event -> {
            ReferenceType type = event.referenceType();
            if (className.equals(type.name())) {
                try {
                    List<Location> locations = type.locationsOfLine(type.defaultStratum(), type.sourceName(), line);
                    if (!locations.isEmpty()) {
                        Location location = locations.get(0);
                        BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
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
