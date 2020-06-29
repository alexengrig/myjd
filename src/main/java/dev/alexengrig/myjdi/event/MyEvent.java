package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.*;
import dev.alexengrig.myjdi.event.delegate.*;
import dev.alexengrig.myjdi.handle.MyEventHandler;

public interface MyEvent extends Event {
    static MyEvent of(Event event) {
        if (event instanceof ExceptionEvent) {
            return new ExceptionEventDelegate((ExceptionEvent) event);
        } else if (event instanceof BreakpointEvent) {
            return new BreakpointEventDelegate((BreakpointEvent) event);
        } else if (event instanceof StepEvent) {
            return new StepEventDelegate((StepEvent) event);
        }
        // watchpoint events
        else if (event instanceof AccessWatchpointEvent) {
            return new AccessWatchpointEventDelegate((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            return new ModificationWatchpointEventDelegate((ModificationWatchpointEvent) event);
        }
        // method events
        else if (event instanceof MethodExitEvent) {
            return new MethodExitEventDelegate((MethodExitEvent) event);
        } else if (event instanceof MethodEntryEvent) {
            return new MethodEntryEventDelegate((MethodEntryEvent) event);
        }
        // monitor events
        else if (event instanceof MonitorWaitedEvent) {
            return new MonitorWaitedEventDelegate((MonitorWaitedEvent) event);
        } else if (event instanceof MonitorWaitEvent) {
            return new MonitorWaitEventDelegate((MonitorWaitEvent) event);
        } else if (event instanceof MonitorContendedEnteredEvent) {
            return new MonitorContendedEnteredEventDelegate((MonitorContendedEnteredEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            return new MonitorContendedEnterEventDelegate((MonitorContendedEnterEvent) event);
        }
        // class events
        else if (event instanceof ClassUnloadEvent) {
            return new ClassUnloadEventDelegate((ClassUnloadEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            return new ClassPrepareEventDelegate((ClassPrepareEvent) event);
        }
        // thread events
        else if (event instanceof ThreadDeathEvent) {
            return new ThreadDeathEventDelegate((ThreadDeathEvent) event);
        } else if (event instanceof ThreadStartEvent) {
            return new ThreadStartEventDelegate((ThreadStartEvent) event);
        }
        // vm events
        else if (event instanceof VMDeathEvent) {
            return new VmDeathEventDelegate((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            return new VmDisconnectEventDelegate((VMDisconnectEvent) event);
        } else if (event instanceof VMStartEvent) {
            return new VmStartEventDelegate((VMStartEvent) event);
        }
        // unexpected
        else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

    default void accept(MyEventHandler handler) {
        handler.handle(this);
    }
}
