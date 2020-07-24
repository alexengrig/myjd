package dev.alexengrig.myjdi.connect;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import dev.alexengrig.myjdi.MyVirtualMachine;
import dev.alexengrig.myjdi.YouthVirtualMachine;

import java.util.HashMap;
import java.util.Map;

public final class YouthLaunchers {
    public static final String HOME = "home";
    public static final String OPTIONS = "options";
    public static final String MAIN = "main";
    public static final String SUSPEND = "suspend";
    public static final String QUOTE = "quote";
    public static final String VMEXEC = "vmexec";
    public static final String COMMAND = "command";
    public static final String ADDRESS = "address";

    private YouthLaunchers() {
    }

//    Command Line

    public static YouthLauncher commandLineLauncher() {
        return launcher(Connectors.commandLineLaunchingConnector());
    }

    public static YouthLauncher commandLineLauncher(VirtualMachineManager vmManager) {
        return launcher(Connectors.commandLineLaunchingConnector(vmManager));
    }

    public static YouthLauncher commandLineLauncher(Map<String, Connector.Argument> arguments) {
        return launcher(Connectors.commandLineLaunchingConnector(), arguments);
    }

    public static YouthLauncher commandLineLauncher(VirtualMachineManager vmManager,
                                                    Map<String, Connector.Argument> arguments) {
        return launcher(Connectors.commandLineLaunchingConnector(vmManager), arguments);
    }

    public static CommandLineLauncherBuilder commandLineLauncherBuilder() {
        return new CommandLineLauncherBuilder(Connectors.commandLineLaunchingConnector());
    }

    public static CommandLineLauncherBuilder commandLineLauncherBuilder(VirtualMachineManager vmManager) {
        return new CommandLineLauncherBuilder(Connectors.commandLineLaunchingConnector(vmManager));
    }

//    Raw Command Line

    public static YouthLauncher rawCommandLineLauncher() {
        return launcher(Connectors.rawCommandLineLaunchingConnector());
    }

    public static YouthLauncher rawCommandLineLauncher(VirtualMachineManager vmManager) {
        return launcher(Connectors.rawCommandLineLaunchingConnector(vmManager));
    }

    public static YouthLauncher rawCommandLineLauncher(Map<String, Connector.Argument> arguments) {
        return launcher(Connectors.rawCommandLineLaunchingConnector(), arguments);
    }

    public static YouthLauncher rawCommandLineLauncher(VirtualMachineManager vmManager,
                                                       Map<String, Connector.Argument> arguments) {
        return launcher(Connectors.rawCommandLineLaunchingConnector(vmManager), arguments);
    }

    public static RawCommandLineLauncherBuilder rawCommandLineLauncherBuilder() {
        return new RawCommandLineLauncherBuilder(Connectors.rawCommandLineLaunchingConnector());
    }

    public static RawCommandLineLauncherBuilder rawCommandLineLauncherBuilder(VirtualMachineManager vmManager) {
        return new RawCommandLineLauncherBuilder(Connectors.rawCommandLineLaunchingConnector(vmManager));
    }

//    Common

    public static YouthLauncher launcher(LaunchingConnector connector) {
        return () -> {
            Map<String, Connector.Argument> args = new HashMap<>(connector.defaultArguments());
            args.putAll(connector.defaultArguments());
            return YouthVirtualMachine.delegate(connector.launch(args));
        };
    }

    public static YouthLauncher launcher(LaunchingConnector connector, Map<String, Connector.Argument> arguments) {
        return () -> {
            Map<String, Connector.Argument> args = new HashMap<>(connector.defaultArguments());
            args.putAll(arguments);
            return new MyVirtualMachine(connector.launch(args));
        };
    }

    public static LauncherBuilder launcherBuilder(LaunchingConnector connector) {
        return new LauncherBuilder(connector);
    }

//    Builder

    public static abstract class BaseLauncherBuilder<B extends BaseLauncherBuilder<B>> {
        protected final LaunchingConnector connector;
        protected final Map<String, Connector.Argument> arguments;

        protected BaseLauncherBuilder(LaunchingConnector connector) {
            this.connector = connector;
            this.arguments = connector.defaultArguments();
        }

        public B argument(String name, String value) {
            arguments.get(name).setValue(value);
            return self();
        }

        protected abstract B self();

        public YouthLauncher build() {
            return () -> YouthVirtualMachine.delegate(connector.launch(arguments));
        }
    }

    public static class LauncherBuilder
            extends BaseLauncherBuilder<LauncherBuilder> {
        protected LauncherBuilder(LaunchingConnector connector) {
            super(connector);
        }

        @Override
        protected LauncherBuilder self() {
            return this;
        }
    }

    public static class CommandLineLauncherBuilder
            extends BaseLauncherBuilder<CommandLineLauncherBuilder> {
        protected CommandLineLauncherBuilder(LaunchingConnector connector) {
            super(connector);
        }

        @Override
        protected CommandLineLauncherBuilder self() {
            return this;
        }

        public CommandLineLauncherBuilder home(String value) {
            return argument(HOME, value);
        }

        public CommandLineLauncherBuilder options(String value) {
            return argument(OPTIONS, value);
        }

        public CommandLineLauncherBuilder main(String value) {
            return argument(MAIN, value);
        }

        public CommandLineLauncherBuilder suspend(String value) {
            return argument(SUSPEND, value);
        }

        public CommandLineLauncherBuilder suspend(boolean value) {
            return suspend(Boolean.toString(value));
        }

        public CommandLineLauncherBuilder quote(String value) {
            return argument(QUOTE, value);
        }

        public CommandLineLauncherBuilder vmexec(String value) {
            return argument(VMEXEC, value);
        }
    }

    public static class RawCommandLineLauncherBuilder
            extends BaseLauncherBuilder<RawCommandLineLauncherBuilder> {
        protected RawCommandLineLauncherBuilder(LaunchingConnector connector) {
            super(connector);
        }

        @Override
        protected RawCommandLineLauncherBuilder self() {
            return this;
        }

        public RawCommandLineLauncherBuilder command(String value) {
            return argument(COMMAND, value);
        }

        public RawCommandLineLauncherBuilder address(String value) {
            return argument(ADDRESS, value);
        }

        public RawCommandLineLauncherBuilder quote(String value) {
            return argument(QUOTE, value);
        }
    }
}
