package dev.alexengrig.myjdi.connector;

import com.sun.jdi.connect.Connector;

import java.util.Map;

public interface MYJDIConnectorArguments {
    boolean hasArgument(String name);

    Connector.Argument addArgument(String name, Connector.Argument argument);

    Connector.Argument getArgument(String name);

    Connector.Argument setArgument(String name, String value);

    Connector.Argument removeArgument(String name);

    Map<String, Connector.Argument> getArguments();
}
