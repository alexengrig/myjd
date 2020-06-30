package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import dev.alexengrig.myjdi.MyVirtualMachine;

import java.io.IOException;

@FunctionalInterface
public interface Attacher {
    MyVirtualMachine attach() throws IOException, IllegalConnectorArgumentsException;
}
