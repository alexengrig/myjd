package dev.alexengrig.myjdi.pretty;

public interface Prettier<T> {
    String pretty(T subject);
}
