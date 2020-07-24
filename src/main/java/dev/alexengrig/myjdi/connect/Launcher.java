package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.YouthVirtualMachine;

import java.io.IOException;

@FunctionalInterface
public interface Launcher {
    YouthVirtualMachine launch() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
