package dev.alexengrig.myjdi.connector;

import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Transport;

import java.util.Map;

public abstract class BaseConnector<T extends Connector> implements MYJDIConnector {
    protected final T connector;

    protected BaseConnector(T connector) {
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
