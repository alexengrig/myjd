package dev.alexengrig.myjd.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ArgsUtil {

    public static final String ARG_PREFIX = "--";

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

    public static Map<String, String> toMap(String[] args) {
        final Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            final String arg = args[i++];
            if (!isArg(arg)) {
                throw new IllegalArgumentException("No arg: " + arg + ".");
            } else if (i == args.length) {
                throw new IllegalArgumentException("No value for arg: " + arg + ".");
            }
            final String value = args[i];
            final String name = getArgName(arg);
            map.put(name, value);
        }
        return map;
    }

    public static boolean isArg(String arg) {
        return arg.startsWith(ARG_PREFIX);
    }

    public static String getArgName(String arg) {
        return arg.substring(ARG_PREFIX.length());
    }
}
