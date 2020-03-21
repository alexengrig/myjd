package dev.alexengrig.myjd.domain;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public interface Option<T> {
    Option<Class<?>> CLASS_NAME = Option.of("className", name -> {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }, true);
    Set<Option<?>> ALL = Collections.singleton(CLASS_NAME);

    static <T> Option<T> of(String name, Function<String, ? extends T> getter, boolean require) {
        return new Option<T>() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public T get(String name) {
                return getter.apply(name);
            }

            @Override
            public boolean require() {
                return require;
            }
        };
    }

    static <T> Option<T> of(String name, Function<String, ? extends T> getter) {
        return of(name, getter, false);
    }

    String name();

    T get(String name);

    boolean require();
}
