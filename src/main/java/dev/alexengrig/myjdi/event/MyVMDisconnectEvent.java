package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDisconnectEvent;

public interface MyVMDisconnectEvent extends MyEvent, VMDisconnectEvent {
}
