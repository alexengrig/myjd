package dev.alexengrig.myjdi.connection;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;

public interface MyConnector extends Connector {
    VirtualMachine connect() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
