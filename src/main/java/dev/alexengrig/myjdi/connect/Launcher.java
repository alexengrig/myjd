package dev.alexengrig.myjdi.connect;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;

@FunctionalInterface
public interface Launcher {
    VirtualMachine launch() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
