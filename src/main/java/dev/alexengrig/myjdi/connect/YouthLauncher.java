package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.io.IOException;

@FunctionalInterface
public interface YouthLauncher {
    YouthVirtualMachine launch() throws IOException, IllegalConnectorArgumentsException, VMStartException;
}
