package dev.alexengrig.myjd.util;

import java.util.Optional;

public final class ArgsUtil {
    private ArgsUtil() {
    }

    public static Optional<String> getValue(String[] args, String name) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (name.equals(arg) && i + 1 < args.length) {
                return Optional.of(args[i + 1]);
            }
        }
        return Optional.empty();
    }
}
