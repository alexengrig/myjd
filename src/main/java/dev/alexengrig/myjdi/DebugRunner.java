package dev.alexengrig.myjdi;

import com.sun.jdi.*;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.connect.YouthConnector;
import dev.alexengrig.myjdi.connect.YouthConnectors;
import dev.alexengrig.myjdi.handle.YouthEventHandleManager;
import dev.alexengrig.myjdi.handle.YouthEventHandler;
import dev.alexengrig.myjdi.request.YouthEventRequestManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getName());

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        String classpath = "./example/build/classes/java/main";
        String mainClass = "dev.alexengrig.example.Main";
        YouthConnector connector = YouthConnectors.commandLine(classpath, mainClass);
        YouthVirtualMachine vm = connector.connect();
        YouthEventRequestManager requestManager = vm.eventRequestManager();
        requestManager.createBreakpointRequest("dev.alexengrig.example.Main", 9);
        YouthEventHandleManager handleManager = vm.eventHandleManager();
        handleManager.createBreakpointHandle(breakpoint -> {
            try {
                List<StackFrame> frames = breakpoint.thread().frames(0, 1);
                StackFrame frame = frames.get(0);
                Map<LocalVariable, Value> variables = frame.getValues(frame.visibleVariables());
                variables.forEach((name, value) -> log.info(name.name() + ": " + value));
            } catch (IncompatibleThreadStateException | AbsentInformationException e) {
                e.printStackTrace();
            }
        });
        YouthEventHandler handler = vm.eventHandler();
        handler.run();
        log.info("Finished.");
    }
}
