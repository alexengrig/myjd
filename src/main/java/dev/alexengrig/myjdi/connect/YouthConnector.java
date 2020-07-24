package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.YouthVirtualMachine;

import java.io.IOException;

public interface YouthConnector extends Connector {
    YouthVirtualMachine connect() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
