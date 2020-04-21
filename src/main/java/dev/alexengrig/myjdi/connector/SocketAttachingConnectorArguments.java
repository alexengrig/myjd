package dev.alexengrig.myjdi.connector;

import com.sun.jdi.connect.Connector;

import java.util.Map;

public class SocketAttachingConnectorArguments extends ConnectorArguments {
    public static final String HOSTNAME = "hostname";
    public static final String PORT = "port";
    public static final String TIMEOUT = "timeout";

    public SocketAttachingConnectorArguments(Map<String, Connector.Argument> arguments) {
        super(arguments);
    }

    public Connector.Argument setHostname(String value) {
        return setArgument(HOSTNAME, value);
    }

    public Connector.Argument setPort(String value) {
        return setArgument(PORT, value);
    }

    public Connector.Argument setPort(int value) {
        return setArgument(PORT, Integer.toString(value));
    }

    public Connector.Argument setTimeout(String value) {
        return setArgument(TIMEOUT, value);
    }

    public Connector.Argument setTimeout(long value) {
        return setArgument(TIMEOUT, Long.toString(value));
    }
}
