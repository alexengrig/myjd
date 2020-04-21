package dev.alexengrig.myjdi.connector;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;
import java.util.Map;

public class CommandLineLaunchingConnector extends BaseConnector<LaunchingConnector> implements MYJDIConnector {
    protected CommandLineLaunchingConnector(LaunchingConnector connector) {
        super(connector);
    }

    @Override
    public VirtualMachine connect(Map<String, ? extends Argument> arguments) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        return null;
    }

    @Override
    public VirtualMachine connect(MYJDIConnectorArguments arguments) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        return null;
    }
}
