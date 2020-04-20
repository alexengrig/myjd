package dev.alexengrig.myjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;

import java.io.IOException;
import java.util.Map;

public class MyDebugger {
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, InterruptedException {
        System.out.println("Started");
        VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
        AttachingConnector connector = vmManager.attachingConnectors().get(0);
        Map<String, Connector.Argument> arguments = connector.defaultArguments();
        arguments.get("port").setValue("8000");
        arguments.get("hostname").setValue("localhost");
        VirtualMachine vm = connector.attach(arguments);
        EventRequestManager requestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = requestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter("dev.alexengrig.example.Main");
        classPrepareRequest.enable();
        EventQueue queue = vm.eventQueue();
        vm.resume();
        boolean running = true;
        while (running) {
            EventSet set = queue.remove();
            for (Event event : set) {
                if (event instanceof ClassPrepareEvent) {
                    System.out.println("Prepare class");
                } else if (event instanceof VMDeathEvent) {
                    System.out.println("Death");
                    running = false;
                    break;
                } else if (event instanceof VMDisconnectedException) {
                    System.out.println("Disconnect");
                    running = false;
                    break;
                } else {
                    System.out.println(event);
                }
            }
            vm.resume();
        }
        System.out.println("Finished");
    }
}
