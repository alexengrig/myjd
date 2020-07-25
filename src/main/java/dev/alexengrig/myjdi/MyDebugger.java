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
import java.util.stream.Collectors;

public class MyDebugger {
    private static final Logger log = Logger.getLogger(MyDebugger.class.getName());

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        String classpath = "./example/build/classes/java/main";
        String mainClass = "dev.alexengrig.example.Main";
        YouthConnector connector = YouthConnectors.commandLine(classpath, mainClass);
        YouthVirtualMachine vm = connector.connect();
        YouthEventHandleManager handleManager = vm.eventHandleManager();
        YouthEventRequestManager requestManager = vm.eventRequestManager();

        handleManager.createBreakpointHandle(breakpoint -> {
            try {
                List<StackFrame> frames = breakpoint.thread().frames(0, 1);
                Map<LocalVariable, Value> variables = frames.get(0).getValues(frames.get(0).visibleVariables());
                log.info("Variables: " + variables.entrySet().stream()
                        .map(e -> e.getKey().name() + ": " + e.getValue())
                        .collect(Collectors.joining("; ")));
            } catch (IncompatibleThreadStateException | AbsentInformationException e) {
                e.printStackTrace();
            }
        });
        requestManager.createBreakpointRequest("dev.alexengrig.example.Main", 9);

        handleManager.createExceptionHandle(event -> {
            String name = event.exception().referenceType().name();
            log.info("Exception: " + name + ", on " + event.location());
        });
        requestManager.createAllExceptionRequest("dev.alexengrig.example.exception.ExampleException");

        YouthEventHandler handler = vm.eventHandler();
        handler.run();
        log.info("Finished.");
    }
}
