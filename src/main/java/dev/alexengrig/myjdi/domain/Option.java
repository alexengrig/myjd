package dev.alexengrig.myjdi.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public interface Option<T> {
    Option<Class<?>> CLASS_NAME = Option.of("className", name -> {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("For 'className' option: ", e);
        }
    }, true);
    Option<Integer[]> BREAK_POINTS = Option.of("breakPoints", string -> {
        try {
            final String[] numbers = string.split("\\s*,\\s*");
            final Integer[] integers = new Integer[numbers.length];
            for (int i = 0, length = numbers.length; i < length; i++) {
                integers[i] = Integer.parseInt(numbers[i]);
            }
            return integers;
        } catch (Exception e) {
            throw new IllegalArgumentException("For 'breakPoints' option: ", e);
        }
    });
    Set<Option<?>> ALL = new HashSet<>(Arrays.asList(CLASS_NAME, BREAK_POINTS));

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
