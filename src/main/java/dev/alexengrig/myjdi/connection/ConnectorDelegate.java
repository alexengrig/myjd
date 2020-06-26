package dev.alexengrig.myjdi.connection;

import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Transport;

import java.util.Map;

public abstract class ConnectorDelegate<T extends Connector> implements MyConnector {
    protected final T connector;

    protected ConnectorDelegate(T connector) {
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
