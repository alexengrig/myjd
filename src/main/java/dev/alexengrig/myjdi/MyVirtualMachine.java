package dev.alexengrig.myjdi;

import com.sun.jdi.*;
import dev.alexengrig.myjdi.event.MyEventQueue;
import dev.alexengrig.myjdi.request.MyEventRequestManager;

import java.util.List;
import java.util.Map;

public interface MyVirtualMachine extends VirtualMachine {
    static MyVirtualMachine delegate(VirtualMachine virtualMachine) {
        return new Delegate(virtualMachine);
    }

    @Override
    MyEventQueue eventQueue();

    @Override
    MyEventRequestManager eventRequestManager();

    class Delegate implements MyVirtualMachine {
        protected final VirtualMachine virtualMachine;

        public Delegate(VirtualMachine virtualMachine) {
            this.virtualMachine = virtualMachine;
        }

        @Override
        public MyEventQueue eventQueue() {
            return MyEventQueue.delegate(virtualMachine.eventQueue());
        }

        @Override
        public MyEventRequestManager eventRequestManager() {
            return MyEventRequestManager.delegate(virtualMachine.eventRequestManager());
        }

        @Override
        public List<ReferenceType> classesByName(String className) {
            return virtualMachine.classesByName(className);
        }

        @Override
        public List<ReferenceType> allClasses() {
            return virtualMachine.allClasses();
        }

        @Override
        public void redefineClasses(Map<? extends ReferenceType, byte[]> classToBytes) {
            virtualMachine.redefineClasses(classToBytes);
        }

        @Override
        public List<ThreadReference> allThreads() {
            return virtualMachine.allThreads();
        }

        @Override
        public void suspend() {
            virtualMachine.suspend();
        }

        @Override
        public void resume() {
            virtualMachine.resume();
        }

        @Override
        public List<ThreadGroupReference> topLevelThreadGroups() {
            return virtualMachine.topLevelThreadGroups();
        }

        @Override
        public BooleanValue mirrorOf(boolean value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public ByteValue mirrorOf(byte value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public CharValue mirrorOf(char value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public ShortValue mirrorOf(short value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public IntegerValue mirrorOf(int value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public LongValue mirrorOf(long value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public FloatValue mirrorOf(float value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public DoubleValue mirrorOf(double value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public StringReference mirrorOf(String value) {
            return virtualMachine.mirrorOf(value);
        }

        @Override
        public VoidValue mirrorOfVoid() {
            return virtualMachine.mirrorOfVoid();
        }

        @Override
        public Process process() {
            return virtualMachine.process();
        }

        @Override
        public void dispose() {
            virtualMachine.dispose();
        }

        @Override
        public void exit(int exitCode) {
            virtualMachine.exit(exitCode);
        }

        @Override
        public boolean canWatchFieldModification() {
            return virtualMachine.canWatchFieldModification();
        }

        @Override
        public boolean canWatchFieldAccess() {
            return virtualMachine.canWatchFieldAccess();
        }

        @Override
        public boolean canGetBytecodes() {
            return virtualMachine.canGetBytecodes();
        }

        @Override
        public boolean canGetSyntheticAttribute() {
            return virtualMachine.canGetSyntheticAttribute();
        }

        @Override
        public boolean canGetOwnedMonitorInfo() {
            return virtualMachine.canGetOwnedMonitorInfo();
        }

        @Override
        public boolean canGetCurrentContendedMonitor() {
            return virtualMachine.canGetCurrentContendedMonitor();
        }

        @Override
        public boolean canGetMonitorInfo() {
            return virtualMachine.canGetMonitorInfo();
        }

        @Override
        public boolean canUseInstanceFilters() {
            return virtualMachine.canUseInstanceFilters();
        }

        @Override
        public boolean canRedefineClasses() {
            return virtualMachine.canRedefineClasses();
        }

        @Override
        public boolean canAddMethod() {
            return virtualMachine.canAddMethod();
        }

        @Override
        public boolean canUnrestrictedlyRedefineClasses() {
            return virtualMachine.canUnrestrictedlyRedefineClasses();
        }

        @Override
        public boolean canPopFrames() {
            return virtualMachine.canPopFrames();
        }

        @Override
        public boolean canGetSourceDebugExtension() {
            return virtualMachine.canGetSourceDebugExtension();
        }

        @Override
        public boolean canRequestVMDeathEvent() {
            return virtualMachine.canRequestVMDeathEvent();
        }

        @Override
        public boolean canGetMethodReturnValues() {
            return virtualMachine.canGetMethodReturnValues();
        }

        @Override
        public boolean canGetInstanceInfo() {
            return virtualMachine.canGetInstanceInfo();
        }

        @Override
        public boolean canUseSourceNameFilters() {
            return virtualMachine.canUseSourceNameFilters();
        }

        @Override
        public boolean canForceEarlyReturn() {
            return virtualMachine.canForceEarlyReturn();
        }

        @Override
        public boolean canBeModified() {
            return virtualMachine.canBeModified();
        }

        @Override
        public boolean canRequestMonitorEvents() {
            return virtualMachine.canRequestMonitorEvents();
        }

        @Override
        public boolean canGetMonitorFrameInfo() {
            return virtualMachine.canGetMonitorFrameInfo();
        }

        @Override
        public boolean canGetClassFileVersion() {
            return virtualMachine.canGetClassFileVersion();
        }

        @Override
        public boolean canGetConstantPool() {
            return virtualMachine.canGetConstantPool();
        }

        @Override
        public String getDefaultStratum() {
            return virtualMachine.getDefaultStratum();
        }

        @Override
        public void setDefaultStratum(String stratum) {
            virtualMachine.setDefaultStratum(stratum);
        }

        @Override
        public long[] instanceCounts(List<? extends ReferenceType> refTypes) {
            return virtualMachine.instanceCounts(refTypes);
        }

        @Override
        public String description() {
            return virtualMachine.description();
        }

        @Override
        public String version() {
            return virtualMachine.version();
        }

        @Override
        public String name() {
            return virtualMachine.name();
        }

        @Override
        public void setDebugTraceMode(int traceFlags) {
            virtualMachine.setDebugTraceMode(traceFlags);
        }

        @Override
        public VirtualMachine virtualMachine() {
            return virtualMachine.virtualMachine();
        }

        @Override
        public String toString() {
            return virtualMachine.toString();
        }
    }
}
