package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Transport;

import java.util.Map;

public abstract class YouthConnectorDelegate<T extends Connector> implements YouthConnector {
    protected final T connector;

    protected YouthConnectorDelegate(T connector) {
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
