package dev.alexengrig.myjd;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;
import dev.alexengrig.myjd.domain.Config;
import dev.alexengrig.myjd.service.Debugger;
import dev.alexengrig.myjd.util.ConfigUtil;

import java.util.Arrays;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Debugger started with args: " + Arrays.toString(args) + ".");
        run(args);
        log.info("Debugger finished.");
    }

    private static void run(String[] args) {
        final Config config = ConfigUtil.getConfig(args);
        final Debugger debugger = new Debugger(config);
        final int[] breakPointLines = {6, 7};
        debugger.setBreakPointLines(breakPointLines);
        try {
            VirtualMachine vm = debugger.connectAndLaunchVM();
            debugger.enableClassPrepareRequest(vm);
            EventSet eventSet;
            while ((eventSet = vm.eventQueue().remove()) != null) {
                for (Event event : eventSet) {
                    if (event instanceof ClassPrepareEvent) {
                        debugger.createBreakpoints(vm, (ClassPrepareEvent) event);
                    }
                    if (event instanceof BreakpointEvent) {
                        debugger.displayVariables((BreakpointEvent) event);
                    }
                    vm.resume();
                }
            }
        } catch (VMDisconnectedException e) {
            log.info("Virtual Machine is disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
