package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.io.IOException;

@FunctionalInterface
public interface YouthAttacher {
    YouthVirtualMachine attach() throws IOException, IllegalConnectorArgumentsException;
}
