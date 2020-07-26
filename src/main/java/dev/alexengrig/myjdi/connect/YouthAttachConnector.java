package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.io.IOException;
import java.util.Map;

public class YouthAttachConnector extends YouthConnectorDelegate<AttachingConnector> {
    protected final YouthAttacher attacher;

    public YouthAttachConnector(AttachingConnector connector) {
        this(connector, YouthAttachers.attacher(connector));
    }

    public YouthAttachConnector(AttachingConnector connector, Map<String, Argument> arguments) {
        this(connector, YouthAttachers.attacher(connector, arguments));
    }

    protected YouthAttachConnector(AttachingConnector connector, YouthAttacher attacher) {
        super(connector);
        this.attacher = attacher;
    }

    @Override
    public YouthVirtualMachine connect() throws IOException, IllegalConnectorArgumentsException {
        return attacher.attach();
    }
}
