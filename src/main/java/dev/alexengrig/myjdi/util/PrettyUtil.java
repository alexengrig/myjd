package dev.alexengrig.myjdi.util;

import com.sun.jdi.connect.Connector;
import dev.alexengrig.myjdi.pretty.ConnectorPrettier;

public final class PrettyUtil {
    private static final ConnectorPrettier CONNECTOR_PRETTIER = new ConnectorPrettier();

    private PrettyUtil() {
    }

    public static String pretty(Connector connector) {
        return CONNECTOR_PRETTIER.pretty(connector);
    }
}
