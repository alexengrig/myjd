package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;

import java.util.Map;

public final class Attachers {
    private Attachers() {
    }

//    Socket

    public static Attacher socketAttacher() {
        return () -> {
            AttachingConnector connector = Connectors.socketAttachingConnector();
            return connector.attach(connector.defaultArguments());
        };
    }

    public static Attacher socketAttacher(VirtualMachineManager vmManager) {
        return () -> {
            AttachingConnector connector = Connectors.socketAttachingConnector(vmManager);
            return connector.attach(connector.defaultArguments());
        };
    }

    public static Attacher socketAttacher(Map<String, Connector.Argument> arguments) {
        return () -> {
            AttachingConnector connector = Connectors.socketAttachingConnector();
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.attach(args);
        };
    }

    public static Attacher socketAttacher(VirtualMachineManager vmManager,
                                          Map<String, Connector.Argument> arguments) {
        return () -> {
            AttachingConnector connector = Connectors.socketAttachingConnector(vmManager);
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.attach(args);
        };
    }

    public static SocketAttacherBuilder socketAttacherBuilder() {
        AttachingConnector connector = Connectors.socketAttachingConnector();
        return new SocketAttacherBuilder(connector.defaultArguments()) {
            @Override
            protected Attacher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.attach(arguments);
            }
        };
    }

    public static SocketAttacherBuilder socketAttacherBuilder(VirtualMachineManager vmManager) {
        AttachingConnector connector = Connectors.socketAttachingConnector(vmManager);
        return new SocketAttacherBuilder(connector.defaultArguments()) {
            @Override
            protected Attacher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.attach(arguments);
            }
        };
    }

//    Builder

    public static abstract class BaseAttacherBuilder<B extends BaseAttacherBuilder<B>> {
        protected final Map<String, Connector.Argument> arguments;

        protected BaseAttacherBuilder(Map<String, Connector.Argument> arguments) {
            this.arguments = arguments;
        }

        public B argument(String name, String value) {
            arguments.get(name).setValue(value);
            return self();
        }

        protected abstract B self();

        public Attacher build() {
            return doBuild(arguments);
        }

        protected abstract Attacher doBuild(Map<String, Connector.Argument> arguments);
    }

    public static abstract class SocketAttacherBuilder extends BaseAttacherBuilder<SocketAttacherBuilder> {
        protected SocketAttacherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected SocketAttacherBuilder self() {
            return this;
        }

        public SocketAttacherBuilder hostname(String value) {
            return argument("hostname", value);
        }

        public SocketAttacherBuilder port(String value) {
            return argument("port", value);
        }

        public SocketAttacherBuilder port(int value) {
            return port(Integer.toString(value));
        }

        public SocketAttacherBuilder timeout(String value) {
            return argument("timeout", value);
        }

        public SocketAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }
}
