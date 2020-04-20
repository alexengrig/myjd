package dev.alexengrig.myjdi;

import com.sun.jdi.event.*;

public class EventQueueHandler implements Runnable {
    protected final EventQueue eventQueue;
    protected boolean connected;

    public EventQueueHandler(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void run() {
        connected = true;
        EventSet eventSet;
        while (connected) {
            try {
                eventSet = eventQueue.remove();
                for (Event event : eventSet) {
                    if (event instanceof VMDisconnectEvent || event instanceof VMDeathEvent) {
                        connected = false;
                        break;
                    } else {
                        System.out.println(event);
                    }
                }
                eventQueue.virtualMachine().resume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
