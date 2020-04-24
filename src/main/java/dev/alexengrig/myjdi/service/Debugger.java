package dev.alexengrig.myjdi.service;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import dev.alexengrig.myjdi.domain.Config;
import dev.alexengrig.myjdi.domain.Option;
import dev.alexengrig.myjdi.request.ClassPrepareRequestSetting;
import dev.alexengrig.myjdi.request.ThreadDeathRequestSetting;
import dev.alexengrig.myjdi.request.ThreadStartRequestSetting;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Debugger extends Thread {
    private static final Logger log = Logger.getLogger(Debugger.class.getSimpleName());
    private static final List<String> EXCLUDED_CLASSES = Arrays.asList(
            "java.*", "javax.*", "sun.*", "com.sun.*", "jdk.*");

    protected final LaunchingConnectorService launchingConnectorService;
    protected final Class<?> debugClass;
    protected final Integer[] breakPointLines;

    protected final ClassPrepareRequestSetting classPrepareRequestSetting;
    protected final ThreadDeathRequestSetting threadDeathRequestSetting;
    protected final ThreadStartRequestSetting threadStartRequestSetting;

    protected VirtualMachine vm;
    protected boolean connected;
    protected boolean died;

    public Debugger(Config config) {
        super("myjdi");
        launchingConnectorService = new LaunchingConnectorService();
        debugClass = config.get(Option.CLASS_NAME);
        breakPointLines = config.get(Option.BREAK_POINTS);
        classPrepareRequestSetting = new ClassPrepareRequestSetting();
        classPrepareRequestSetting.addClassExclusionFilters(EXCLUDED_CLASSES);
        classPrepareRequestSetting.setSuspendAll();
        classPrepareRequestSetting.enable();
        threadDeathRequestSetting = new ThreadDeathRequestSetting();
        threadDeathRequestSetting.setSuspendAll();
        threadDeathRequestSetting.disable();
        threadStartRequestSetting = new ThreadStartRequestSetting();
        threadStartRequestSetting.setSuspendAll();
        threadStartRequestSetting.disable();
    }

    @Override
    public void run() {
        prepare();
        final EventQueue eventQueue = vm.eventQueue();
        while (connected) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    handleEvent(event);
                }
                vm.resume(); //FIXME: call it after all or after each event handling?
            } catch (VMDisconnectedException e) {
                log.severe(String.format("VM is disconnected: %s", e.getMessage()));
                handleVMDisconnectedException();
            } catch (InterruptedException ignore) {
                log.severe("Interrupted.");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    protected void prepare() {
        prepareVm();
        prepareRequests();
    }

    protected void prepareVm() {
        final LaunchingConnector launchingConnector = launchingConnectorService.connect();
//        log.info(String.format("Connector:%n%s", PrettyUtil.pretty(launchingConnector)));
//        String value = "-cp \"C:\\Users\\Gadmin\\Projects\\myjdi\\example\\build\\classes\\java\\main\\dev\\alexengrig\\example\" " +
//                "dev.alexengrig.example.Main";
        final Map<String, String> values = Collections.singletonMap("main", debugClass.getName());
//        log.info(String.format("Connector values: %s.", values));
        final Map<String, Connector.Argument> arguments = launchingConnectorService.arguments(launchingConnector, values);
        try {
            vm = launchingConnector.launch(arguments);
            vm.setDebugTraceMode(VirtualMachine.TRACE_NONE);
            connected = true;
        } catch (IOException e) {
            throw new Error(String.format("Unable to launch target VM: %s.", e));
        } catch (IllegalConnectorArgumentsException e) {
            throw new Error(String.format("Internal error: %s.", e));
        } catch (VMStartException e) {
            throw new Error(String.format("Target VM failed to initialize: %s.", e.getMessage()));
        }
    }

    protected void prepareRequests() {
//        enableExceptionRequest(null, true, true);
        // method
//        enableMethodExitRequest();
//        enableMethodEntryRequest();
        // monitor
        /*enableMonitorWaitedRequest();
        enableMonitorWaitRequest();
        enableMonitorContendedEnteredRequest();
        enableMonitorContendedEnterRequest();*/
        // class
//        enableClassUnloadRequest();
        createClassPrepareRequest();
        // thread
        createThreadDeathRequest();
        createThreadStartRequest();
    }

    protected void enableExceptionRequest(ReferenceType refType, boolean notifyCaught, boolean notifyUncaught) {
        final EventRequestManager manager = vm.eventRequestManager();
        final ExceptionRequest request = manager.createExceptionRequest(refType, notifyCaught, notifyUncaught);
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableBreakpointRequest(Location location) {
        final EventRequestManager manager = vm.eventRequestManager();
        final BreakpointRequest request = manager.createBreakpointRequest(location);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableStepRequest(ThreadReference thread, int size, int depth) {
        final EventRequestManager manager = vm.eventRequestManager();
        final StepRequest request = manager.createStepRequest(thread, size, depth);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Watchpoint requests

    protected void enableAccessWatchpointRequest(Field field) {
        final EventRequestManager manager = vm.eventRequestManager();
        final AccessWatchpointRequest request = manager.createAccessWatchpointRequest(field);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableModificationWatchpointRequest(Field field) {
        final EventRequestManager manager = vm.eventRequestManager();
        final ModificationWatchpointRequest request = manager.createModificationWatchpointRequest(field);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Method requests

    protected void enableMethodExitRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MethodExitRequest request = manager.createMethodExitRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMethodEntryRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MethodEntryRequest request = manager.createMethodEntryRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Monitor requests

    protected void enableMonitorWaitedRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorWaitedRequest request = manager.createMonitorWaitedRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMonitorWaitRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorWaitRequest request = manager.createMonitorWaitRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMonitorContendedEnteredRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorContendedEnteredRequest request = manager.createMonitorContendedEnteredRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMonitorContendedEnterRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorContendedEnterRequest request = manager.createMonitorContendedEnterRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Class requests

    protected void enableClassUnloadRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ClassUnloadRequest request = manager.createClassUnloadRequest();
        for (String excludedClass : EXCLUDED_CLASSES) {
            request.addClassExclusionFilter(excludedClass);
        }
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void createClassPrepareRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ClassPrepareRequest request = manager.createClassPrepareRequest();
        classPrepareRequestSetting.apply(request);
    }

//    Thread requests

    protected void createThreadDeathRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ThreadDeathRequest request = manager.createThreadDeathRequest();
        threadDeathRequestSetting.apply(request);
    }

    protected void createThreadStartRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ThreadStartRequest request = manager.createThreadStartRequest();
        threadStartRequestSetting.apply(request);
    }

//    Handling

    protected void handleEvent(Event event) {
        if (event instanceof ExceptionEvent) {
            handleExceptionEvent((ExceptionEvent) event);
        } else if (event instanceof BreakpointEvent) {
            handleBreakpointEvent((BreakpointEvent) event);
        } else if (event instanceof StepEvent) {
            handleStepEvent((StepEvent) event);
        }
        // watchpoint events
        else if (event instanceof AccessWatchpointEvent) {
            handleAccessWatchpointEvent((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            handleModificationWatchpointEvent((ModificationWatchpointEvent) event);
        }
        // method events
        else if (event instanceof MethodExitEvent) {
            handleMethodExitEvent((MethodExitEvent) event);
        } else if (event instanceof MethodEntryEvent) {
            handleMethodEntryEvent((MethodEntryEvent) event);
        }
        // monitor events
        else if (event instanceof MonitorWaitedEvent) {
            handleMonitorWaitedEvent((MonitorWaitedEvent) event);
        } else if (event instanceof MonitorWaitEvent) {
            handleMonitorWaitEvent((MonitorWaitEvent) event);
        } else if (event instanceof MonitorContendedEnteredEvent) {
            handleMonitorContendedEnteredEvent((MonitorContendedEnteredEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            handleMonitorContendedEnterEvent((MonitorContendedEnterEvent) event);
        }
        // class events
        else if (event instanceof ClassUnloadEvent) {
            handleClassUnloadEvent((ClassUnloadEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            handleClassPrepareEvent((ClassPrepareEvent) event);
        }
        // thread events
        else if (event instanceof ThreadDeathEvent) {
            handleThreadDeathEvent((ThreadDeathEvent) event);
        } else if (event instanceof ThreadStartEvent) {
            handleThreadStartEvent((ThreadStartEvent) event);
        }
        // vm events
        else if (event instanceof VMDeathEvent) {
            handleVmDeathEvent((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            handleVmDisconnectEvent((VMDisconnectEvent) event);
        } else if (event instanceof VMStartEvent) {
            handleVmStartEvent((VMStartEvent) event);
        }
        // unexpected
        else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

    protected void handleExceptionEvent(ExceptionEvent event) {
        final ObjectReference exception = event.exception();
        final Location location = event.catchLocation();
        log.info(String.format("Exception: %s; in location: %s.", exception, location));
    }

    protected void handleBreakpointEvent(BreakpointEvent event) {
        log.info(String.format("Breakpoint: %s.", event.location()));
    }

    protected void handleStepEvent(StepEvent event) {
        log.info(String.format("Step: %s.", event.location()));
    }

//    Watchpoint events

    protected void handleAccessWatchpointEvent(AccessWatchpointEvent event) {
        final Field field = event.field();
        final Value value = event.valueCurrent();
        log.info(String.format("Field accessed: %s; with value: %s.", field, value));
    }

    protected void handleModificationWatchpointEvent(ModificationWatchpointEvent event) {
        final Field field = event.field();
        final Value oldValue = event.valueCurrent();
        final Value newValue = event.valueToBe();
        log.info(String.format("Field modified: %s; from: %s; to: %s.", field, oldValue, newValue));

    }

//    Method events

    protected void handleMethodExitEvent(MethodExitEvent event) {
        final Method method = event.method();
        final Value value = event.returnValue();
        log.info(String.format("Method: %s; return: %s.", method, value));
    }

    protected void handleMethodEntryEvent(MethodEntryEvent event) {
        final Method method = event.method();
//        log.info(String.format("Method called: %s.", method));
        try {
            for (Location location : method.allLineLocations()) {
                enableBreakpointRequest(location);
            }
        } catch (AbsentInformationException e) {
            e.printStackTrace(); //TODO: Add handling
        }
    }

//    Monitor events

    protected void handleMonitorWaitedEvent(MonitorWaitedEvent event) {
        log.info("MonitorWaitedEvent."); //TODO: Add implementation
    }

    protected void handleMonitorWaitEvent(MonitorWaitEvent event) {
        log.info("MonitorWaitEvent."); //TODO: Add implementation
    }

    protected void handleMonitorContendedEnteredEvent(MonitorContendedEnteredEvent event) {
        log.info("MonitorContendedEnteredEvent."); //TODO: Add implementation
    }

    protected void handleMonitorContendedEnterEvent(MonitorContendedEnterEvent event) {
        log.info("MonitorContendedEnterEvent."); //TODO: Add implementation
    }

//    Class events

    protected void handleClassUnloadEvent(ClassUnloadEvent event) {
        log.info(String.format("Class unloaded: %s.", event.className()));
    }

    protected void handleClassPrepareEvent(ClassPrepareEvent event) {
        final ReferenceType type = event.referenceType();
        log.info(String.format("Class prepared: %s.", type));
        for (Field field : type.allFields()) {
            enableAccessWatchpointRequest(field);
            enableModificationWatchpointRequest(field);
        }
        System.out.println("List: " + vm.classesByName("dev.alexengrig.myjdi.ExampleDebuggee"));
        System.out.println("Prop: " + event.request().getProperty("test"));
//        final EventRequestManager manager = vm.eventRequestManager();
//        final List<Field> fields = event.referenceType().visibleFields();
//        for (Field field : fields) {
//            final ModificationWatchpointRequest request = manager.createModificationWatchpointRequest(field);
//            request.setSuspendPolicy(EventRequest.SUSPEND_NONE);
//            request.enable();
//        }
//        ClassType classType = (ClassType) event.referenceType();
//        for (int lineNumber : breakPointLines) {
//            List<Location> locations = classType.locationsOfLine(lineNumber);
//            if (!locations.isEmpty()) {
//                Location location = locations.get(0);
//                EventRequestManager eventRequestManager = vm.eventRequestManager();
//                BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
//                breakpointRequest.enable();
//            }
//        }
    }

//    Thread events

    protected void handleThreadDeathEvent(ThreadDeathEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("Thread terminated: %s.", thread));
    }

    protected void handleThreadStartEvent(ThreadStartEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("Thread started: %s.", thread));
    }

//    Vm events

    protected void handleVmDeathEvent(VMDeathEvent event) {
        died = true;
        log.info("VM finished.");
    }

    protected void handleVmDisconnectEvent(VMDisconnectEvent event) {
        connected = false;
        if (!died) {
            log.info("VM disconnected.");
        }
    }

    protected void handleVmStartEvent(VMStartEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("VM started in thread: %s.", thread));
    }


    /***
     * A VMDisconnectedException has happened while dealing with
     * another event. We need to flush the event queue, dealing only
     * with exit events (VMDeath, VMDisconnect) so that we terminate
     * correctly.
     */
    protected synchronized void handleVMDisconnectedException() {
        final EventQueue eventQueue = vm.eventQueue();
        while (connected) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    if (event instanceof VMDeathEvent) {
                        handleVmDeathEvent((VMDeathEvent) event);
                    } else if (event instanceof VMDisconnectEvent) {
                        handleVmDisconnectEvent((VMDisconnectEvent) event);
                    }
                }
                vm.resume(); //FIXME: call it after all or after each event handling?
            } catch (InterruptedException ignore) {
                log.severe("Interrupted.");
            }
        }
    }
}
