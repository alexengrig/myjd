package dev.alexengrig.myjdi.connector;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import java.io.IOException;
import java.util.Map;

public class MYJDIAttachingConnector extends BaseConnector<AttachingConnector> implements MYJDIConnector {
    protected MYJDIAttachingConnector(AttachingConnector connector) {
        super(connector);
    }

    @Override
    public VirtualMachine connect(Map<String, ? extends Argument> arguments) throws IOException, IllegalConnectorArgumentsException {
        return connector.attach(arguments);
    }
}
