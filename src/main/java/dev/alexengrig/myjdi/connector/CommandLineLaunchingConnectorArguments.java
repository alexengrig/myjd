package dev.alexengrig.myjdi.connector;

import com.sun.jdi.connect.Connector;

import java.util.Map;

public class CommandLineLaunchingConnectorArguments extends ConnectorArguments {
    public static final String HOME = "home";
    public static final String OPTIONS = "options";
    public static final String MAIN = "main";
    public static final String SUSPEND = "suspend";
    public static final String QUOTE = "quote";
    public static final String VMEXEC = "vmexec";

    public CommandLineLaunchingConnectorArguments(Map<String, Connector.Argument> arguments) {
        super(arguments);
    }

    public Connector.Argument setHome(String value) {
        return setArgument(HOME, value);
    }

    public Connector.Argument setOptions(String value) {
        return setArgument(OPTIONS, value);
    }

    public Connector.Argument setMain(String value) {
        return setArgument(MAIN, value);
    }

    public Connector.Argument setSuspend(String value) {
        return setArgument(SUSPEND, value);
    }

    public Connector.Argument setSuspend(boolean value) {
        return setArgument(SUSPEND, Boolean.toString(value));
    }

    public Connector.Argument setQuote(String value) {
        return setArgument(QUOTE, value);
    }

    public Connector.Argument setVMExec(String value) {
        return setArgument(VMEXEC, value);
    }
}
