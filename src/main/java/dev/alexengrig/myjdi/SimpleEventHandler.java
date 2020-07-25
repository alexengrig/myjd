package dev.alexengrig.myjdi;

import com.sun.jdi.VMDisconnectedException;
import dev.alexengrig.myjdi.event.YouthEvent;
import dev.alexengrig.myjdi.handle.OmitEventHandler;
import dev.alexengrig.myjdi.queue.YouthEventIterator;
import dev.alexengrig.myjdi.queue.YouthEventQueue;

public class SimpleEventHandler extends OmitEventHandler implements Runnable {
    protected final YouthEventQueue eventQueue;

    protected boolean running;
    protected boolean vmDied;
    protected boolean disconnected;

    public SimpleEventHandler(YouthVirtualMachine virtualMachine) {
        this.eventQueue = virtualMachine.eventQueue();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                YouthEventIterator iterator = eventQueue.remove().eventIterator();
                while (iterator.hasNext()) {
                    YouthEvent event = iterator.nextEvent();
                    event.accept(this);
                }
                eventQueue.virtualMachine().resume();
            } catch (InterruptedException | VMDisconnectedException ignore) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
