package dev.alexengrig.myjdi.event;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.*;
import dev.alexengrig.myjdi.event.delegate.BreakpointEventDelegate;
import dev.alexengrig.myjdi.handle.MyEventHandler;
import dev.alexengrig.myjdi.request.MyEventRequest;

import java.util.Objects;

public interface MyEvent extends Event {
    static MyEvent findOut(Event event) {
        if (event instanceof ExceptionEvent) {
            return new MyExceptionEvent.Delegate((ExceptionEvent) event);
        } else if (event instanceof BreakpointEvent) {
            return new BreakpointEventDelegate((BreakpointEvent) event);
        } else if (event instanceof StepEvent) {
            return new MyStepEvent.Delegate((StepEvent) event);
        }
        // watchpoint events
        else if (event instanceof AccessWatchpointEvent) {
            return new MyAccessWatchpointEvent.Delegate((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            return new MyModificationWatchpointEvent.Delegate((ModificationWatchpointEvent) event);
        }
        // method events
        else if (event instanceof MethodExitEvent) {
            return new MyMethodExitEvent.Delegate((MethodExitEvent) event);
        } else if (event instanceof MethodEntryEvent) {
            return new MyMethodEntryEvent.Delegate((MethodEntryEvent) event);
        }
        // monitor events
        else if (event instanceof MonitorWaitedEvent) {
            return new MyMonitorWaitedEvent.Delegate((MonitorWaitedEvent) event);
        } else if (event instanceof MonitorWaitEvent) {
            return new MyMonitorWaitEvent.Delegate((MonitorWaitEvent) event);
        } else if (event instanceof MonitorContendedEnteredEvent) {
            return new MyMonitorContendedEnteredEvent.Delegate((MonitorContendedEnteredEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            return new MyMonitorContendedEnterEvent.Delegate((MonitorContendedEnterEvent) event);
        }
        // class events
        else if (event instanceof ClassUnloadEvent) {
            return new MyClassUnloadEvent.Delegate((ClassUnloadEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            return new MyClassPrepareEvent.Delegate((ClassPrepareEvent) event);
        }
        // thread events
        else if (event instanceof ThreadDeathEvent) {
            return new MyThreadDeathEvent.Delegate((ThreadDeathEvent) event);
        } else if (event instanceof ThreadStartEvent) {
            return new MyThreadStartEvent.Delegate((ThreadStartEvent) event);
        }
        // vm events
        else if (event instanceof VMDeathEvent) {
            return new MyVMDeathEvent.Delegate((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            return new MyVMDisconnectEvent.Delegate((VMDisconnectEvent) event);
        } else if (event instanceof VMStartEvent) {
            return new MyVMStartEvent.Delegate((VMStartEvent) event);
        }
        // unexpected
        else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

    static MyEvent delegate(Event event) {
        return new Delegate<>(event);
    }

    @Override
    MyEventRequest request();

    default void accept(MyEventHandler handler) {
        handler.handle(this);
    }

    class Delegate<E extends Event> implements MyEvent {
        protected final E event;

        public Delegate(E event) {
            this.event = Objects.requireNonNull(event, "The event must not be null");
        }

        @Override
        public MyEventRequest request() {
            return MyEventRequest.delegate(event.request());
        }

        @Override
        public VirtualMachine virtualMachine() {
            return event.virtualMachine();
        }

        @Override
        public String toString() {
            return event.toString();
        }
    }
}
