package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import dev.alexengrig.myjdi.MyVirtualMachine;

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
    public MyVirtualMachine connect() throws IOException, IllegalConnectorArgumentsException {
        return attacher.attach();
    }
}
