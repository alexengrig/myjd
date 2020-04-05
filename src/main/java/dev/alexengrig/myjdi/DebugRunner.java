package dev.alexengrig.myjdi;

import dev.alexengrig.myjdi.domain.Config;
import dev.alexengrig.myjdi.service.Debugger;
import dev.alexengrig.myjdi.util.ConfigUtil;

import java.util.Arrays;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    public static void main(String[] args) {
        log.info(String.format("Debugger started with args: %s.", Arrays.toString(args)));
        final Config config = ConfigUtil.getConfig(args);
        final Debugger debugger = new Debugger(config);
        debugger.start();
        try {
            debugger.join();
        } catch (InterruptedException ignore) {
        }
        log.info("Debugger finished.");
    }

}
