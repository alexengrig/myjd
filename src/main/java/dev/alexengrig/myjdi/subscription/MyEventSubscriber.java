package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.event.YouthEvent;

import java.util.function.Consumer;

public class MyEventSubscriber<E extends YouthEvent> implements YouthEventSubscriber<E> {
    protected final Consumer<E> handler;

    public MyEventSubscriber(Consumer<E> handler) {
        this.handler = handler;
    }

    @Override
    public void accept(E event) {
        handler.accept(event);
    }
}
