package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.MyVirtualMachine;

import java.io.IOException;

@FunctionalInterface
public interface Launcher {
    MyVirtualMachine launch() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
