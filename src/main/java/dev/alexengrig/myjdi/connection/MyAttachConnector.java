package dev.alexengrig.myjdi.connection;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import java.io.IOException;
import java.util.Map;

public class MyAttachConnector extends ConnectorDelegate<AttachingConnector> {
    protected final Attacher attacher;

    public MyAttachConnector(AttachingConnector connector) {
        this(connector, Attachers.attacher(connector));
    }

    public MyAttachConnector(AttachingConnector connector, Map<String, Argument> arguments) {
        this(connector, Attachers.attacher(connector, arguments));
    }

    protected MyAttachConnector(AttachingConnector connector, Attacher attacher) {
        super(connector);
        this.attacher = attacher;
    }

    @Override
    public VirtualMachine connect() throws IOException, IllegalConnectorArgumentsException {
        return attacher.attach();
    }
}
