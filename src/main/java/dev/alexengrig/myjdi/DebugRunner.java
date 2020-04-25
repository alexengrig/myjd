package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjdi.util.MyConnectors;

import java.io.IOException;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getName());

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        VirtualMachine vm = MyConnectors.socket("localhost", 8000).connect();
        EventRequestManager requestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = requestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter("dev.alexengrig.example.Main");
        classPrepareRequest.enable();
        new EventQueueHandler(vm.eventQueue()).run();
        log.info("Finished.");
    }
}
