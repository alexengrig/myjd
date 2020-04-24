package dev.alexengrig.myjdi.util;

import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Transport;
import dev.alexengrig.myjdi.MyConnector;

import java.util.Map;

public abstract class ProxyConnector<T extends Connector> implements MyConnector {
    protected final T connector;

    protected ProxyConnector(T connector) {
        this.connector = connector;
    }

    @Override
    public String name() {
        return connector.name();
    }

    @Override
    public String description() {
        return connector.description();
    }

    @Override
    public Transport transport() {
        return connector.transport();
    }

    @Override
    public Map<String, Argument> defaultArguments() {
        return connector.defaultArguments();
    }
}
