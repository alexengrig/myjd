package dev.alexengrig.myjdi;

import com.sun.jdi.*;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.request.StepRequest;
import dev.alexengrig.myjdi.connect.YouthConnector;
import dev.alexengrig.myjdi.connect.YouthConnectors;
import dev.alexengrig.myjdi.handle.YouthEventHandler;
import dev.alexengrig.myjdi.request.YouthEventRequestManager;
import dev.alexengrig.myjdi.subscription.YouthEventSubscriptionManager;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.sun.jdi.request.StepRequest.STEP_MIN;
import static com.sun.jdi.request.StepRequest.STEP_OVER;

public class MyDebugger {
    private static final Logger log = Logger.getLogger(MyDebugger.class.getName());

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        String classpath = "./example/build/classes/java/main";
        String mainClass = "dev.alexengrig.example.Main";
        YouthConnector connector = YouthConnectors.commandLine(classpath, mainClass);
        YouthVirtualMachine vm = connector.connect();
        YouthEventRequestManager requestManager = vm.eventRequestManager();
        YouthEventSubscriptionManager subscriptionManager = vm.eventSubscriptionManager();

        subscriptionManager.subscribeOnException(event -> {
            String name = event.exception().referenceType().name();
            log.info("Exception: " + name + ", on " + event.location());
        });
        requestManager.createAllExceptionRequest("dev.alexengrig.example.exception.ExampleException");

        subscriptionManager.subscribeOnBreakpoint(breakpoint -> {
            try {
                log.info("Breakpoint on " + breakpoint.location());
                List<StackFrame> frames = breakpoint.thread().frames(0, 1);
                Map<LocalVariable, Value> variables = frames.get(0).getValues(frames.get(0).visibleVariables());
                log.info("Variables: " + variables.entrySet().stream()
                        .map(e -> e.getKey().name() + ": " + e.getValue())
                        .collect(Collectors.joining("; ")));
                StepRequest stepRequest = requestManager.createStepRequest(breakpoint.thread(), STEP_MIN, STEP_OVER);
                stepRequest.addCountFilter(1);
                stepRequest.enable();
            } catch (IncompatibleThreadStateException | AbsentInformationException e) {
                e.printStackTrace();
            }
        });
        subscriptionManager.subscribeOnStep(step -> log.info("Step to " + step.location()));
        requestManager.createBreakpointRequest("dev.alexengrig.example.Main", 12);

        YouthEventHandler handler = vm.eventHandler();
        handler.run();
        log.info("Finished.");
    }
}
