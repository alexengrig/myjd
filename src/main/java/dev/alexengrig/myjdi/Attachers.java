package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;

import java.util.Map;

public final class Attachers {
    public static final String HOSTNAME = "hostname";
    public static final String PORT = "port";
    public static final String TIMEOUT = "timeout";
    public static final String NAME = "name";

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

//    Shared Memory

    public static Attacher sharedMemoryAttacher() {
        return () -> {
            AttachingConnector connector = Connectors.sharedMemoryAttachingConnector();
            return connector.attach(connector.defaultArguments());
        };
    }

    public static Attacher sharedMemoryAttacher(VirtualMachineManager vmManager) {
        return () -> {
            AttachingConnector connector = Connectors.sharedMemoryAttachingConnector(vmManager);
            return connector.attach(connector.defaultArguments());
        };
    }

    public static Attacher sharedMemoryAttacher(Map<String, Connector.Argument> arguments) {
        return () -> {
            AttachingConnector connector = Connectors.sharedMemoryAttachingConnector();
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.attach(args);
        };
    }

    public static Attacher sharedMemoryAttacher(VirtualMachineManager vmManager,
                                                Map<String, Connector.Argument> arguments) {
        return () -> {
            AttachingConnector connector = Connectors.sharedMemoryAttachingConnector(vmManager);
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.attach(args);
        };
    }

    public static SharedMemoryAttacherBuilder sharedMemoryAttacherBuilder() {
        AttachingConnector connector = Connectors.sharedMemoryAttachingConnector();
        return new SharedMemoryAttacherBuilder(connector.defaultArguments()) {
            @Override
            protected Attacher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.attach(arguments);
            }
        };
    }

    public static SharedMemoryAttacherBuilder sharedMemoryAttacherBuilder(VirtualMachineManager vmManager) {
        AttachingConnector connector = Connectors.sharedMemoryAttachingConnector(vmManager);
        return new SharedMemoryAttacherBuilder(connector.defaultArguments()) {
            @Override
            protected Attacher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.attach(arguments);
            }
        };
    }

//    Common

    public static Attacher attacher(AttachingConnector connector) {
        return () -> connector.attach(connector.defaultArguments());
    }

    public static Attacher attacher(AttachingConnector connector, Map<String, Connector.Argument> arguments) {
        return () -> {
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.attach(args);
        };
    }

    public static AttacherBuilder attacherBuilder(AttachingConnector connector) {
        return new AttacherBuilder(connector.defaultArguments()) {
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

    public static abstract class AttacherBuilder extends BaseAttacherBuilder<AttacherBuilder> {
        protected AttacherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected AttacherBuilder self() {
            return this;
        }
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
            return argument(HOSTNAME, value);
        }

        public SocketAttacherBuilder port(String value) {
            return argument(PORT, value);
        }

        public SocketAttacherBuilder port(int value) {
            return port(Integer.toString(value));
        }

        public SocketAttacherBuilder timeout(String value) {
            return argument(TIMEOUT, value);
        }

        public SocketAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }

    public static abstract class SharedMemoryAttacherBuilder extends BaseAttacherBuilder<SharedMemoryAttacherBuilder> {
        protected SharedMemoryAttacherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected SharedMemoryAttacherBuilder self() {
            return this;
        }

        public SharedMemoryAttacherBuilder name(String value) {
            return argument(NAME, value);
        }

        public SharedMemoryAttacherBuilder timeout(String value) {
            return argument(TIMEOUT, value);
        }

        public SharedMemoryAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }
}
