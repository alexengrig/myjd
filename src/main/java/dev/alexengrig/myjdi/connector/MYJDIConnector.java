package dev.alexengrig.myjdi.connector;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;
import java.util.Map;

public interface MYJDIConnector extends Connector {
    VirtualMachine connect(Map<String, ? extends Connector.Argument> arguments) throws IOException, IllegalConnectorArgumentsException, VMStartException;

    default VirtualMachine connect(MYJDIConnectorArguments arguments) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        return connect(arguments.getArguments());
    }
}
