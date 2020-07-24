package dev.alexengrig.myjdi.event;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.*;
import dev.alexengrig.myjdi.event.delegate.YouthBreakpointEventDelegate;
import dev.alexengrig.myjdi.handle.YouthEventHandler;
import dev.alexengrig.myjdi.request.MyEventRequest;

import java.util.Objects;

public interface YouthEvent extends Event {
    static YouthEvent findOut(Event event) {
        if (event instanceof ExceptionEvent) {
            return new YouthExceptionEvent.Delegate((ExceptionEvent) event);
        } else if (event instanceof BreakpointEvent) {
            return new YouthBreakpointEventDelegate((BreakpointEvent) event);
        } else if (event instanceof StepEvent) {
            return new YouthStepEvent.Delegate((StepEvent) event);
        }
        // watchpoint events
        else if (event instanceof AccessWatchpointEvent) {
            return new YouthAccessWatchpointEvent.Delegate((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            return new YouthModificationWatchpointEvent.Delegate((ModificationWatchpointEvent) event);
        }
        // method events
        else if (event instanceof MethodExitEvent) {
            return new YouthMethodExitEvent.Delegate((MethodExitEvent) event);
        } else if (event instanceof MethodEntryEvent) {
            return new YouthMethodEntryEvent.Delegate((MethodEntryEvent) event);
        }
        // monitor events
        else if (event instanceof MonitorWaitedEvent) {
            return new YouthMonitorWaitedEvent.Delegate((MonitorWaitedEvent) event);
        } else if (event instanceof MonitorWaitEvent) {
            return new YouthMonitorWaitEvent.Delegate((MonitorWaitEvent) event);
        } else if (event instanceof MonitorContendedEnteredEvent) {
            return new YouthMonitorContendedEnteredEvent.Delegate((MonitorContendedEnteredEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            return new YouthMonitorContendedEnterEvent.Delegate((MonitorContendedEnterEvent) event);
        }
        // class events
        else if (event instanceof ClassUnloadEvent) {
            return new YouthClassUnloadEvent.Delegate((ClassUnloadEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            return new YouthClassPrepareEvent.Delegate((ClassPrepareEvent) event);
        }
        // thread events
        else if (event instanceof ThreadDeathEvent) {
            return new YouthThreadDeathEvent.Delegate((ThreadDeathEvent) event);
        } else if (event instanceof ThreadStartEvent) {
            return new YouthThreadStartEvent.Delegate((ThreadStartEvent) event);
        }
        // vm events
        else if (event instanceof VMDeathEvent) {
            return new YouthVMDeathEvent.Delegate((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            return new YouthVMDisconnectEvent.Delegate((VMDisconnectEvent) event);
        } else if (event instanceof VMStartEvent) {
            return new YouthVMStartEvent.Delegate((VMStartEvent) event);
        }
        // unexpected
        else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

    static YouthEvent delegate(Event event) {
        return new Delegate<>(event);
    }

    @Override
    MyEventRequest request();

    default void accept(YouthEventHandler handler) {
        handler.handle(this);
    }

    class Delegate<E extends Event> implements YouthEvent {
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
