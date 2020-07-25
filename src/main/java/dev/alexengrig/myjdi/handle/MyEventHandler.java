package dev.alexengrig.myjdi.handle;

import dev.alexengrig.myjdi.YouthVirtualMachine;
import dev.alexengrig.myjdi.event.*;
import dev.alexengrig.myjdi.queue.YouthEventIterator;
import dev.alexengrig.myjdi.queue.YouthEventQueue;

import java.util.logging.Logger;

public class MyEventHandler extends OmitEventHandler implements YouthEventHandler {
    protected static final Logger log = Logger.getLogger(MyEventHandle.class.getName());

    protected final YouthVirtualMachine virtualMachine;
    protected boolean running;
    protected boolean disconnected;
    protected boolean died;
    protected boolean interrupted;

    public MyEventHandler(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    @Override
    public void run() {
        running = true;
        final YouthEventQueue queue = virtualMachine.eventQueue();
        YouthEventIterator iterator;
        YouthEvent event;
        while (running) {
            try {
                iterator = queue.remove().eventIterator();
                while (iterator.hasNext()) {
                    event = iterator.next();
                    event.accept(this);
                }
                virtualMachine.resume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
                interrupted = true;
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void handleVmStart(YouthVMStartEvent event) {
        log.info("VM is started.");
    }

    @Override
    public void handleVmDeath(YouthVMDeathEvent ignore) {
        died = true;
        running = false;
        log.warning("VM is died.");
    }

    @Override
    public void handleVmDisconnect(YouthVMDisconnectEvent ignore) {
        if (!died) {
            disconnected = true;
            log.warning("VM is disconnected.");
        }
        running = false;
    }

    @Override
    public void handleClassPrepare(YouthClassPrepareEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfClassPrepare(event);
    }

    @Override
    public void handleBreakpoint(YouthBreakpointEvent event) {
        virtualMachine.eventSubscriptionManager().notifyOfBreakpoint(event);
    }
}
