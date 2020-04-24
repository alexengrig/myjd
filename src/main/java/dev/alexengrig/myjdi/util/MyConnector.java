package dev.alexengrig.myjdi.util;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;

public interface MyConnector extends Connector {
    VirtualMachine connect() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
