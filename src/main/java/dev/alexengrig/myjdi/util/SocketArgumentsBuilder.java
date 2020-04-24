package dev.alexengrig.myjdi.util;

import com.sun.jdi.connect.Connector;

import java.util.Map;

public class SocketArgumentsBuilder extends ArgumentsBuilder<SocketArgumentsBuilder> {
    public static final String HOSTNAME = "hostname";
    public static final String PORT = "port";
    public static final String TIMEOUT = "timeout";

    protected SocketArgumentsBuilder(Map<String, Connector.Argument> source) {
        super(source);
    }

    public static SocketArgumentsBuilder from(Map<String, Connector.Argument> source) {
        return new SocketArgumentsBuilder(source);
    }

    public SocketArgumentsBuilder hostname(String value) {
        return argument(HOSTNAME, value);
    }

    public SocketArgumentsBuilder port(String value) {
        return argument(PORT, value);
    }

    public SocketArgumentsBuilder port(int value) {
        return port(Integer.toString(value));
    }

    public SocketArgumentsBuilder timeout(String value) {
        return argument(TIMEOUT, value);
    }

    public SocketArgumentsBuilder timeout(long value) {
        return timeout(Long.toString(value));
    }

    @Override
    protected SocketArgumentsBuilder self() {
        return this;
    }
}
