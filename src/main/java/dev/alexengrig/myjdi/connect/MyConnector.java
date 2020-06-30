package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.MyVirtualMachine;

import java.io.IOException;

public interface MyConnector extends Connector {
    MyVirtualMachine connect() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
