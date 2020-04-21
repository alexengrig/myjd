package dev.alexengrig.myjdi.connector;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;
import java.util.Map;

public class BaseLaunchingConnector extends BaseConnector<LaunchingConnector> implements MYJDIConnector {
    protected BaseLaunchingConnector(LaunchingConnector connector) {
        super(connector);
    }

    @Override
    public VirtualMachine connect(Map<String, ? extends Argument> arguments) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        return connector.launch(arguments);
    }
}
