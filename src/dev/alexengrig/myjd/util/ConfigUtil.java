package dev.alexengrig.myjd.util;

import dev.alexengrig.myjd.domain.Config;
import dev.alexengrig.myjd.domain.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ConfigUtil {
    private ConfigUtil() {
    }

    public static Config getConfig(String[] args) {
        return getConfig(args, Option.ALL);
    }

    public static Config getConfig(String[] args, Set<Option<?>> options) {
        final Map<String, String> argsMap = ArgsUtil.toMap(args);
        final Map<String, Object> properties = new HashMap<>();
        for (Option<?> option : options) {
            final String name = option.name();
            final String value = argsMap.get(name);
            if (value == null && option.require()) {
                throw new IllegalArgumentException("No require arg: " + name);
            }
            properties.put(name, option.get(value));
        }
        return new Config(properties);
    }
}
