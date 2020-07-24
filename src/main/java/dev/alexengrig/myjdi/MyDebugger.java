package dev.alexengrig.myjdi;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjdi.event.YouthEventHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MyDebugger implements Runnable {
    private static final Logger log = Logger.getLogger(MyDebugger.class.getName());

    private final EventRequestManager eventRequestManager;
    private final EventQueueHandler eventQueueHandler;

    private final Map<String, ClassPrepareRequest> classPrepareRequestMap;

    public MyDebugger(VirtualMachine vm) {
        eventRequestManager = vm.eventRequestManager();
        eventQueueHandler = new EventQueueHandler(vm.eventQueue());
        classPrepareRequestMap = new HashMap<>();
        init();
    }

    private void init() {
    }

    @Override
    public void run() {
        eventQueueHandler.run();
    }

    public void addBreakpoint(String className, int line) {
        if (!classPrepareRequestMap.containsKey(className)) {
            ClassPrepareRequest request = eventRequestManager.createClassPrepareRequest();
            request.addClassFilter(className);
            request.enable();
            classPrepareRequestMap.put(className, request);
        }
        eventQueueHandler.addClassPrepareEventListener(e -> {
            ReferenceType type = e.referenceType();
            if (className.equals(type.name())) {
                try {
                    List<Location> locations = type.locationsOfLine(type.defaultStratum(), type.sourceName(), line);
                    if (!locations.isEmpty()) {
                        Location location = locations.get(0);
                        BreakpointRequest request = eventRequestManager.createBreakpointRequest(location);
                        request.enable();
                    }
                } catch (AbsentInformationException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void addBreakpointHandler(YouthEventHandler<BreakpointEvent> handler) {
        eventQueueHandler.addBreakpointEventListener(handler);
    }
}
