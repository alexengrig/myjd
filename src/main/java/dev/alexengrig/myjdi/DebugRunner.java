package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.connection.MyConnector;
import dev.alexengrig.myjdi.connection.MyConnectors;

import java.io.IOException;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getName());

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        MyConnector connector = MyConnectors.socket("localhost", 8000);
        VirtualMachine vm = connector.connect();
        MyDebugger debugger = new MyDebugger(vm);
        debugger.addBreakpoint("dev.alexengrig.example.Main", 9);
        debugger.addBreakpoint("dev.alexengrig.example.Main", 14);
        debugger.run();
        log.info("Finished.");
    }
}
