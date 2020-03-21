package dev.alexengrig.myjd.domain;

import java.util.Map;

public class Config {
    private final Map<String, ?> properties;

    public Config(Map<String, ?> properties) {
        this.properties = properties;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) properties.get(name);
    }

    public <T> T get(Option<T> option) {
        return get(option.name());
    }
}
