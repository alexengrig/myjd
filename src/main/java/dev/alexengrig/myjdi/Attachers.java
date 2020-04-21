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
    public static final String PID = "pid";

    private Attachers() {
    }

//    Socket

    public static Attacher socketAttacher() {
        return attacher(Connectors.socketAttachingConnector());
    }

    public static Attacher socketAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.socketAttachingConnector(vmManager));
    }

    public static Attacher socketAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.socketAttachingConnector(), arguments);
    }

    public static Attacher socketAttacher(VirtualMachineManager vmManager,
                                          Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.socketAttachingConnector(vmManager), arguments);
    }

    public static SocketAttacherBuilder socketAttacherBuilder() {
        return new SocketAttacherBuilder(Connectors.socketAttachingConnector());
    }

    public static SocketAttacherBuilder socketAttacherBuilder(VirtualMachineManager vmManager) {
        return new SocketAttacherBuilder(Connectors.socketAttachingConnector(vmManager));
    }

//    Shared Memory

    public static Attacher sharedMemoryAttacher() {
        return attacher(Connectors.sharedMemoryAttachingConnector());
    }

    public static Attacher sharedMemoryAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.sharedMemoryAttachingConnector(vmManager));
    }

    public static Attacher sharedMemoryAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.sharedMemoryAttachingConnector(), arguments);
    }

    public static Attacher sharedMemoryAttacher(VirtualMachineManager vmManager,
                                                Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.sharedMemoryAttachingConnector(vmManager), arguments);
    }

    public static SharedMemoryAttacherBuilder sharedMemoryAttacherBuilder() {
        return new SharedMemoryAttacherBuilder(Connectors.sharedMemoryAttachingConnector());
    }

    public static SharedMemoryAttacherBuilder sharedMemoryAttacherBuilder(VirtualMachineManager vmManager) {
        return new SharedMemoryAttacherBuilder(Connectors.sharedMemoryAttachingConnector(vmManager));
    }

//    Process

    public static Attacher processAttacher() {
        return attacher(Connectors.processAttachingConnector());
    }

    public static Attacher processAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.processAttachingConnector(vmManager));
    }

    public static Attacher processAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.processAttachingConnector(), arguments);
    }

    public static Attacher processAttacher(VirtualMachineManager vmManager,
                                           Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.processAttachingConnector(vmManager), arguments);
    }

    public static ProcessAttacherBuilder processAttacherBuilder() {
        return new ProcessAttacherBuilder(Connectors.processAttachingConnector());
    }

    public static ProcessAttacherBuilder processAttacherBuilder(VirtualMachineManager vmManager) {
        return new ProcessAttacherBuilder(Connectors.processAttachingConnector(vmManager));
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
        return new AttacherBuilder(connector);
    }

//    Builder

    public static abstract class BaseAttacherBuilder<B extends BaseAttacherBuilder<B>> {
        protected final AttachingConnector connector;
        protected final Map<String, Connector.Argument> arguments;

        public BaseAttacherBuilder(AttachingConnector connector) {
            this.connector = connector;
            this.arguments = connector.defaultArguments();
        }

        public B argument(String name, String value) {
            arguments.get(name).setValue(value);
            return self();
        }

        protected abstract B self();

        public Attacher build() {
            return () -> connector.attach(arguments);
        }
    }

    public static class AttacherBuilder extends BaseAttacherBuilder<AttacherBuilder> {
        public AttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected AttacherBuilder self() {
            return this;
        }
    }

    public static class SocketAttacherBuilder extends BaseAttacherBuilder<SocketAttacherBuilder> {
        public SocketAttacherBuilder(AttachingConnector connector) {
            super(connector);
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

    public static class SharedMemoryAttacherBuilder extends BaseAttacherBuilder<SharedMemoryAttacherBuilder> {
        public SharedMemoryAttacherBuilder(AttachingConnector connector) {
            super(connector);
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

    public static class ProcessAttacherBuilder extends BaseAttacherBuilder<ProcessAttacherBuilder> {
        public ProcessAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected ProcessAttacherBuilder self() {
            return this;
        }

        public ProcessAttacherBuilder pid(String value) {
            return argument(PID, value);
        }

        public ProcessAttacherBuilder pid(int value) {
            return pid(Integer.toString(value));
        }

        public ProcessAttacherBuilder timeout(String value) {
            return argument(TIMEOUT, value);
        }

        public ProcessAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }
}
