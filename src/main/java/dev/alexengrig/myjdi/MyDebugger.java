package dev.alexengrig.myjdi;

import com.sun.jdi.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
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
                    List<Location> locations = type.locationsOfLine(line);
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
        eventQueueHandler.addBreakpointEventListener(e -> {
            try {
                StackFrame frame = e.thread().frame(0);
                Map<LocalVariable, Value> values = frame.getValues(frame.visibleVariables());
                StringJoiner joiner = new StringJoiner("\n");
                for (Map.Entry<LocalVariable, Value> entry : values.entrySet()) {
                    joiner.add(entry.getKey().name() + " = " + entry.getValue());
                }
                log.info("Stack:\n" + joiner.toString());
            } catch (IncompatibleThreadStateException | AbsentInformationException ex) {
                ex.printStackTrace();
            }

        });
    }
}
