package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.Connector;

import java.util.HashMap;
import java.util.Map;

public abstract class ArgumentsBuilder<B extends ArgumentsBuilder<B>> {
    protected final Map<String, Connector.Argument> arguments;

    public ArgumentsBuilder(Map<String, Connector.Argument> source) {
        this.arguments = new HashMap<>(source);
    }

    public Map<String, Connector.Argument> build() {
        return arguments;
    }

    protected B argument(String key, String value) {
        arguments.get(key).setValue(value);
        return self();
    }

    protected abstract B self();
}
