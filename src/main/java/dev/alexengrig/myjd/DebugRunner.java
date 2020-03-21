package dev.alexengrig.myjd;

import dev.alexengrig.myjd.domain.Config;
import dev.alexengrig.myjd.service.Debugger;
import dev.alexengrig.myjd.util.ConfigUtil;

import java.util.Arrays;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    public static void main(String[] args) {
        log.info("DebugRunner started with args: " + Arrays.toString(args) + ".");
        final Config config = ConfigUtil.getConfig(args);
        final Debugger debugger = new Debugger(config);
        debugger.run();
        log.info("DebugRunner finished.");
    }

}
