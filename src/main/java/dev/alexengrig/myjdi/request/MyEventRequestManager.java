package dev.alexengrig.myjdi.request;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.ExceptionRequest;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.util.List;

public class MyEventRequestManager extends YouthEventRequestManager.Delegate implements YouthEventRequestManager {
    protected final YouthVirtualMachine virtualMachine;

    public MyEventRequestManager(YouthVirtualMachine virtualMachine, EventRequestManager eventRequestManager) {
        super(eventRequestManager);
        this.virtualMachine = virtualMachine;
    }

    @Override
    public void createExceptionRequest(String className, boolean notifyCaught, boolean notifyUncaught) {
        ClassPrepareRequest classPrepareRequest = createClassPrepareRequest();
        classPrepareRequest.addClassFilter(className);
        classPrepareRequest.enable();
        virtualMachine.eventSubscriptionManager().subscribeOnClassPrepare(event -> {
            ReferenceType referenceType = event.referenceType();
            if (referenceType.name().equals(className)) {
                ExceptionRequest exceptionRequest = createExceptionRequest(referenceType, notifyCaught, notifyUncaught);
                exceptionRequest.enable();
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
        ClassPrepareRequest classPrepareRequest = createClassPrepareRequest();
        classPrepareRequest.addClassFilter(className);
        classPrepareRequest.enable();
        virtualMachine.eventSubscriptionManager().subscribeOnClassPrepare(event -> {
            ReferenceType type = event.referenceType();
            if (className.equals(type.name())) {
                try {
                    List<Location> locations = type.locationsOfLine(type.defaultStratum(), type.sourceName(), line);
                    if (!locations.isEmpty()) {
                        Location location = locations.get(0);
                        BreakpointRequest breakpointRequest = createBreakpointRequest(location);
                        breakpointRequest.enable();
                    }
                } catch (AbsentInformationException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
