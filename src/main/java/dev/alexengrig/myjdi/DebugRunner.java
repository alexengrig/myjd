package dev.alexengrig.myjdi;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.connect.YouthConnector;
import dev.alexengrig.myjdi.connect.YouthConnectors;
import dev.alexengrig.myjdi.event.*;
import dev.alexengrig.myjdi.handle.OmitEventHandler;
import dev.alexengrig.myjdi.handle.YouthClassPrepareHandle;
import dev.alexengrig.myjdi.handle.YouthEventHandleManager;
import dev.alexengrig.myjdi.request.YouthEventRequestManager;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getName());

    private static boolean running = true;

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        String classpath = "./example/build/classes/java/main";
        String mainClass = "dev.alexengrig.example.Main";
        YouthConnector connector = YouthConnectors.commandLine(classpath, mainClass);
        YouthVirtualMachine vm = connector.connect();
        YouthEventRequestManager requestManager = vm.eventRequestManager();
        requestManager.createBreakpointRequest("dev.alexengrig.example.Main", 9);
        YouthEventHandleManager handleManager = vm.eventHandleManager();
        YouthEventQueue queue = vm.eventQueue();
        while (running) {
            try {
                YouthEventSet set = queue.remove();
                YouthEventIterator iterator = set.eventIterator();
                List<YouthClassPrepareHandle> classPrepareHandles = handleManager.classPrepareHandles();
                while (iterator.hasNext()) {
                    YouthEvent event = iterator.nextEvent();
                    event.accept(new OmitEventHandler() {
                        @Override
                        public void handleClassPrepare(YouthClassPrepareEvent event) {
                            classPrepareHandles.forEach(h -> h.handle(event));
                        }

                        @Override
                        public void handleVmDeath(YouthVMDeathEvent event) {
                            running = false;
                            log.info("VM Died.");
                        }

                        @Override
                        public void handleVmDisconnect(YouthVMDisconnectEvent event) {
                            running = false;
                            log.info("VM Disconnected");
                        }
                    });
                }
                vm.resume();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        log.info("Finished.");
    }
}
