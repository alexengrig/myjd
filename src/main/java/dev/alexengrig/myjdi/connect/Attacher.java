package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import dev.alexengrig.myjdi.YouthVirtualMachine;

import java.io.IOException;

@FunctionalInterface
public interface Attacher {
    YouthVirtualMachine attach() throws IOException, IllegalConnectorArgumentsException;
}
