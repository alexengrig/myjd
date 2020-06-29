package dev.alexengrig.myjdi.event;

import com.sun.jdi.event.VMDeathEvent;

public interface MyVMDeathEvent extends MyEvent, VMDeathEvent {
}
