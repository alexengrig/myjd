package dev.alexengrig.myjdi.connect;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import java.util.Map;

public final class YouthConnectors {
    private YouthConnectors() {
    }

//    Popular

    public static YouthConnector commandLine(String classpath, String mainClass) {
        LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
        Map<String, Connector.Argument> arguments = connector.defaultArguments();
        arguments.get("options").setValue("-cp " + classpath);
        arguments.get("main").setValue(mainClass);
        return connector(connector, arguments);
    }

    public static YouthConnector socket(int port) {
        AttachingConnector connector = Connectors.socketAttachingConnector();
        return connector(connector, SocketArgumentsBuilder.from(connector.defaultArguments())
                .port(port)
                .build());
    }

    public static YouthConnector socket(String hostname, int port) {
        AttachingConnector connector = Connectors.socketAttachingConnector();
        return connector(connector, SocketArgumentsBuilder.from(connector.defaultArguments())
                .hostname(hostname)
                .port(port)
                .build());
    }

//    Launcher

    public static YouthConnector commandLineLaunchingConnector() {
        return connector(Connectors.commandLineLaunchingConnector());
    }

    public static YouthConnector commandLineLaunchingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.commandLineLaunchingConnector(vmManger));
    }

    public static YouthConnector rawCommandLineLaunchingConnector() {
        return connector(Connectors.rawCommandLineLaunchingConnector());
    }

    public static YouthConnector rawCommandLineLaunchingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.rawCommandLineLaunchingConnector(vmManger));
    }

    public static YouthConnector connector(LaunchingConnector connector) {
        return new YouthLaunchConnector(connector);
    }

    public static YouthConnector connector(LaunchingConnector connector, Map<String, Connector.Argument> arguments) {
        return new YouthLaunchConnector(connector, arguments);
    }

//    Attacher

    public static YouthConnector socketAttachingConnector() {
        return connector(Connectors.socketAttachingConnector());
    }

    public static YouthConnector socketAttachingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.socketAttachingConnector(vmManger));
    }

    public static YouthConnector sharedMemoryAttachingConnector() {
        return connector(Connectors.sharedMemoryAttachingConnector());
    }

    public static YouthConnector sharedMemoryAttachingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.sharedMemoryAttachingConnector(vmManger));
    }

    public static YouthConnector processAttachingConnector() {
        return connector(Connectors.processAttachingConnector());
    }

    public static YouthConnector processAttachingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.processAttachingConnector(vmManger));
    }

    public static YouthConnector connector(AttachingConnector connector) {
        return new YouthAttachConnector(connector);
    }

    public static YouthConnector connector(AttachingConnector connector, Map<String, Connector.Argument> arguments) {
        return new YouthAttachConnector(connector, arguments);
    }
}
