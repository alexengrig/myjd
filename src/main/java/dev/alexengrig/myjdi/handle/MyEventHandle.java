package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.Event;

import java.util.function.Consumer;

public class MyEventHandle<E extends Event> implements YouthEventHandle<E> {
    protected final Consumer<E> handler;
    protected boolean enabled;

    public MyEventHandle(Consumer<E> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(E event) {
        handler.accept(event);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
