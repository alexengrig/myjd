package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import java.util.Map;

public final class Launchers {

//    Command Line

    public static Launcher commandLineLauncher() {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
            return connector.launch(connector.defaultArguments());
        };
    }

    public static Launcher commandLineLauncher(VirtualMachineManager vmManager) {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector(vmManager);
            return connector.launch(connector.defaultArguments());
        };
    }

    public static Launcher commandLineLauncher(Map<String, Connector.Argument> arguments) {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static Launcher commandLineLauncher(VirtualMachineManager vmManager,
                                               Map<String, Connector.Argument> arguments) {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector(vmManager);
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static CommandLineLauncherBuilder commandLineLauncherBuilder() {
        final LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
        return new CommandLineLauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

    public static CommandLineLauncherBuilder commandLineLauncherBuilder(VirtualMachineManager vmManager) {
        final LaunchingConnector connector = Connectors.commandLineLaunchingConnector(vmManager);
        return new CommandLineLauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

//    Builder

    public static abstract class BaseLauncherBuilder<B extends BaseLauncherBuilder<B>> {
        protected final Map<String, Connector.Argument> arguments;

        protected BaseLauncherBuilder(Map<String, Connector.Argument> arguments) {
            this.arguments = arguments;
        }

        public B argument(String name, String value) {
            arguments.get(name).setValue(value);
            return self();
        }

        protected abstract B self();

        public Launcher build() {
            return doBuild(arguments);
        }

        protected abstract Launcher doBuild(Map<String, Connector.Argument> arguments);
    }

    public static abstract class CommandLineLauncherBuilder
            extends BaseLauncherBuilder<CommandLineLauncherBuilder> {
        public CommandLineLauncherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected CommandLineLauncherBuilder self() {
            return this;
        }

        public CommandLineLauncherBuilder home(String value) {
            return argument("home", value);
        }

        public CommandLineLauncherBuilder options(String value) {
            return argument("options", value);
        }

        public CommandLineLauncherBuilder main(String value) {
            return argument("main", value);
        }

        public CommandLineLauncherBuilder suspend(String value) {
            return argument("suspend", value);
        }

        public CommandLineLauncherBuilder suspend(boolean value) {
            return suspend(Boolean.toString(value));
        }

        public CommandLineLauncherBuilder quote(String value) {
            return argument("quote", value);
        }

        public CommandLineLauncherBuilder vmexec(String value) {
            return argument("vmexec", value);
        }
    }
}
