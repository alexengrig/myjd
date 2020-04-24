package dev.alexengrig.myjdi;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import dev.alexengrig.myjdi.util.MyConnector;
import dev.alexengrig.myjdi.util.MyConnectors;

import java.io.IOException;

public class MyDebugger {
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, InterruptedException, VMStartException {
        System.out.println("Started");
        MyConnector connector = MyConnectors.socket("localhost", 8000);
        VirtualMachine vm = connector.connect();
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
