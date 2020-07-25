package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.event.YouthEvent;

import java.util.function.Consumer;

@FunctionalInterface
public interface YouthEventSubscriber<E extends YouthEvent> extends Consumer<E> {
    @Override
    void accept(E event);
}
