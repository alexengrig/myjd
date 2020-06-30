package dev.alexengrig.myjdi.request;

import com.sun.jdi.*;
import com.sun.jdi.request.*;

import java.util.List;

public interface MyEventRequestManager extends EventRequestManager {
    static MyEventRequestManager delegate(EventRequestManager requestManager) {
        return new Delegate(requestManager);
    }

    class Delegate implements MyEventRequestManager {
        protected final EventRequestManager requestManager;

        public Delegate(EventRequestManager requestManager) {
            this.requestManager = requestManager;
        }

        @Override
        public ClassPrepareRequest createClassPrepareRequest() {
            return requestManager.createClassPrepareRequest();
        }

        @Override
        public ClassUnloadRequest createClassUnloadRequest() {
            return requestManager.createClassUnloadRequest();
        }

        @Override
        public ThreadStartRequest createThreadStartRequest() {
            return requestManager.createThreadStartRequest();
        }

        @Override
        public ThreadDeathRequest createThreadDeathRequest() {
            return requestManager.createThreadDeathRequest();
        }

        @Override
        public ExceptionRequest createExceptionRequest(ReferenceType refType, boolean notifyCaught, boolean notifyUncaught) {
            return requestManager.createExceptionRequest(refType, notifyCaught, notifyUncaught);
        }

        @Override
        public MethodEntryRequest createMethodEntryRequest() {
            return requestManager.createMethodEntryRequest();
        }

        @Override
        public MethodExitRequest createMethodExitRequest() {
            return requestManager.createMethodExitRequest();
        }

        @Override
        public MonitorContendedEnterRequest createMonitorContendedEnterRequest() {
            return requestManager.createMonitorContendedEnterRequest();
        }

        @Override
        public MonitorContendedEnteredRequest createMonitorContendedEnteredRequest() {
            return requestManager.createMonitorContendedEnteredRequest();
        }

        @Override
        public MonitorWaitRequest createMonitorWaitRequest() {
            return requestManager.createMonitorWaitRequest();
        }

        @Override
        public MonitorWaitedRequest createMonitorWaitedRequest() {
            return requestManager.createMonitorWaitedRequest();
        }

        @Override
        public StepRequest createStepRequest(ThreadReference thread, int size, int depth) {
            return requestManager.createStepRequest(thread, size, depth);
        }

        @Override
        public BreakpointRequest createBreakpointRequest(Location location) {
            return requestManager.createBreakpointRequest(location);
        }

        @Override
        public AccessWatchpointRequest createAccessWatchpointRequest(Field field) {
            return requestManager.createAccessWatchpointRequest(field);
        }

        @Override
        public ModificationWatchpointRequest createModificationWatchpointRequest(Field field) {
            return requestManager.createModificationWatchpointRequest(field);
        }

        @Override
        public VMDeathRequest createVMDeathRequest() {
            return requestManager.createVMDeathRequest();
        }

        @Override
        public void deleteEventRequest(EventRequest eventRequest) {
            requestManager.deleteEventRequest(eventRequest);
        }

        @Override
        public void deleteEventRequests(List<? extends EventRequest> eventRequests) {
            requestManager.deleteEventRequests(eventRequests);
        }

        @Override
        public void deleteAllBreakpoints() {
            requestManager.deleteAllBreakpoints();
        }

        @Override
        public List<StepRequest> stepRequests() {
            return requestManager.stepRequests();
        }

        @Override
        public List<ClassPrepareRequest> classPrepareRequests() {
            return requestManager.classPrepareRequests();
        }

        @Override
        public List<ClassUnloadRequest> classUnloadRequests() {
            return requestManager.classUnloadRequests();
        }

        @Override
        public List<ThreadStartRequest> threadStartRequests() {
            return requestManager.threadStartRequests();
        }

        @Override
        public List<ThreadDeathRequest> threadDeathRequests() {
            return requestManager.threadDeathRequests();
        }

        @Override
        public List<ExceptionRequest> exceptionRequests() {
            return requestManager.exceptionRequests();
        }

        @Override
        public List<BreakpointRequest> breakpointRequests() {
            return requestManager.breakpointRequests();
        }

        @Override
        public List<AccessWatchpointRequest> accessWatchpointRequests() {
            return requestManager.accessWatchpointRequests();
        }

        @Override
        public List<ModificationWatchpointRequest> modificationWatchpointRequests() {
            return requestManager.modificationWatchpointRequests();
        }

        @Override
        public List<MethodEntryRequest> methodEntryRequests() {
            return requestManager.methodEntryRequests();
        }

        @Override
        public List<MethodExitRequest> methodExitRequests() {
            return requestManager.methodExitRequests();
        }

        @Override
        public List<MonitorContendedEnterRequest> monitorContendedEnterRequests() {
            return requestManager.monitorContendedEnterRequests();
        }

        @Override
        public List<MonitorContendedEnteredRequest> monitorContendedEnteredRequests() {
            return requestManager.monitorContendedEnteredRequests();
        }

        @Override
        public List<MonitorWaitRequest> monitorWaitRequests() {
            return requestManager.monitorWaitRequests();
        }

        @Override
        public List<MonitorWaitedRequest> monitorWaitedRequests() {
            return requestManager.monitorWaitedRequests();
        }

        @Override
        public List<VMDeathRequest> vmDeathRequests() {
            return requestManager.vmDeathRequests();
        }

        @Override
        public VirtualMachine virtualMachine() {
            return requestManager.virtualMachine();
        }

        @Override
        public String toString() {
            return requestManager.toString();
        }
    }
}
