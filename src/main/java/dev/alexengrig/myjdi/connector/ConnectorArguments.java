package dev.alexengrig.myjdi.connector;

import com.sun.jdi.connect.Connector;

import java.util.HashMap;
import java.util.Map;

public class ConnectorArguments implements MYJDIConnectorArguments {
    protected final Map<String, Connector.Argument> values;

    protected ConnectorArguments(Map<String, Connector.Argument> arguments) {
        values = new HashMap<>(arguments);
    }

    @Override
    public boolean hasArgument(String name) {
        return values.containsKey(name);
    }

    @Override
    public Connector.Argument addArgument(String name, Connector.Argument argument) {
        return values.put(name, argument);
    }

    @Override
    public Connector.Argument getArgument(String name) {
        return values.get(name);
    }

    @Override
    public Connector.Argument setArgument(String name, String value) {
        Connector.Argument argument = values.get(name);
        argument.setValue(value);
        return argument;
    }

    @Override
    public Connector.Argument removeArgument(String name) {
        return values.remove(name);
    }

    @Override
    public Map<String, Connector.Argument> getArguments() {
        return values;
    }
}
