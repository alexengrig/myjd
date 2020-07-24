package dev.alexengrig.myjdi;

import com.sun.jdi.VMDisconnectedException;
import dev.alexengrig.myjdi.event.MyEvent;
import dev.alexengrig.myjdi.event.MyEventIterator;
import dev.alexengrig.myjdi.event.YouthEventQueue;
import dev.alexengrig.myjdi.handle.OmitEventHandler;

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
                MyEventIterator iterator = eventQueue.remove().eventIterator();
                while (iterator.hasNext()) {
                    MyEvent event = iterator.nextEvent();
                    event.accept(this);
                }
                eventQueue.virtualMachine().resume();
            } catch (InterruptedException | VMDisconnectedException ignore) {
                break;
            }
        }
    }
}
