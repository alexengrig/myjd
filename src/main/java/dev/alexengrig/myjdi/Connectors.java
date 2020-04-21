package dev.alexengrig.myjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.ListeningConnector;

public final class Connectors {
    public static final String COMMAND_LINE_LAUNCH = "com.sun.jdi.CommandLineLaunch";
    public static final String RAW_COMMAND_LINE_LAUNCH = "com.sun.jdi.RawCommandLineLaunch";

    public static final String SOCKET_ATTACH = "com.sun.jdi.SocketAttach";
    public static final String SHARED_MEMORY_ATTACH = "com.sun.jdi.SharedMemoryAttach";
    public static final String PROCESS_ATTACH = "com.sun.jdi.ProcessAttach";
    public static final String SA_CORE_ATTACH = "sun.jvm.hotspot.jdi.SACoreAttachingConnector";
    public static final String SA_PID_ATTACH = "sun.jvm.hotspot.jdi.SAPIDAttachingConnector";
    public static final String SA_DEBUG_SERVER_ATTACH = "sun.jvm.hotspot.jdi.SADebugServerAttachingConnector";

    public static final String SOCKET_LISTEN = "com.sun.jdi.SocketListen";
    public static final String SHARED_MEMORY_LISTEN = "com.sun.jdi.SharedMemoryListen";

//    Launch

    public static LaunchingConnector commandLineLaunchingConnector() {
        return launchingConnector(COMMAND_LINE_LAUNCH);
    }

    public static LaunchingConnector commandLineLaunchingConnector(VirtualMachineManager vmManager) {
        return launchingConnector(vmManager, COMMAND_LINE_LAUNCH);
    }

    public static LaunchingConnector rawCommandLineLaunchingConnector() {
        return launchingConnector(RAW_COMMAND_LINE_LAUNCH);
    }

    public static LaunchingConnector rawCommandLineLaunchingConnector(VirtualMachineManager vmManager) {
        return launchingConnector(vmManager, RAW_COMMAND_LINE_LAUNCH);
    }

    public static LaunchingConnector launchingConnector(String name) {
        return launchingConnector(Bootstrap.virtualMachineManager(), name);
    }

    public static LaunchingConnector launchingConnector(VirtualMachineManager vmManager, String name) {
        for (LaunchingConnector connector : vmManager.launchingConnectors()) {
            if (name.equals(connector.name())) {
                return connector;
            }
        }
        throw new IllegalArgumentException("Unknown launching connector name: " + name);
    }

//    Attach

    public static AttachingConnector socketAttachingConnector() {
        return attachingConnector(SOCKET_ATTACH);
    }

    public static AttachingConnector socketAttachingConnector(VirtualMachineManager vmManager) {
        return attachingConnector(vmManager, SOCKET_ATTACH);
    }

    public static AttachingConnector sharedMemoryAttachingConnector() {
        return attachingConnector(SHARED_MEMORY_ATTACH);
    }

    public static AttachingConnector sharedMemoryAttachingConnector(VirtualMachineManager vmManager) {
        return attachingConnector(vmManager, SHARED_MEMORY_ATTACH);
    }

    public static AttachingConnector processAttachingConnector() {
        return attachingConnector(PROCESS_ATTACH);
    }

    public static AttachingConnector processAttachingConnector(VirtualMachineManager vmManager) {
        return attachingConnector(vmManager, PROCESS_ATTACH);
    }

    public static AttachingConnector saCoreAttachingConnector() {
        return attachingConnector(SA_CORE_ATTACH);
    }

    public static AttachingConnector saCoreAttachingConnector(VirtualMachineManager vmManager) {
        return attachingConnector(vmManager, SA_CORE_ATTACH);
    }

    public static AttachingConnector saPIDAttachingConnector() {
        return attachingConnector(SA_PID_ATTACH);
    }

    public static AttachingConnector saPIDAttachingConnector(VirtualMachineManager vmManager) {
        return attachingConnector(vmManager, SA_PID_ATTACH);
    }

    public static AttachingConnector saDebugServerAttachingConnector() {
        return attachingConnector(SA_DEBUG_SERVER_ATTACH);
    }

    public static AttachingConnector saDebugServerAttachingConnector(VirtualMachineManager vmManager) {
        return attachingConnector(vmManager, SA_DEBUG_SERVER_ATTACH);
    }

    public static AttachingConnector attachingConnector(String name) {
        return attachingConnector(Bootstrap.virtualMachineManager(), name);
    }

    public static AttachingConnector attachingConnector(VirtualMachineManager vmManager, String name) {
        for (AttachingConnector connector : vmManager.attachingConnectors()) {
            if (name.equals(connector.name())) {
                return connector;
            }
        }
        throw new IllegalArgumentException("Unknown attaching connector name: " + name);
    }

//    Listen

    public static ListeningConnector socketListeningConnector() {
        return listeningConnector(SOCKET_LISTEN);
    }

    public static ListeningConnector socketListeningConnector(VirtualMachineManager vmManager) {
        return listeningConnector(vmManager, SOCKET_LISTEN);
    }

    public static ListeningConnector sharedMemoryListeningConnector() {
        return listeningConnector(SHARED_MEMORY_LISTEN);
    }

    public static ListeningConnector sharedMemoryListeningConnector(VirtualMachineManager vmManager) {
        return listeningConnector(vmManager, SHARED_MEMORY_LISTEN);
    }

    public static ListeningConnector listeningConnector(String name) {
        return listeningConnector(Bootstrap.virtualMachineManager(), name);
    }

    public static ListeningConnector listeningConnector(VirtualMachineManager vmManager, String name) {
        for (ListeningConnector connector : vmManager.listeningConnectors()) {
            if (name.equals(connector.name())) {
                return connector;
            }
        }
        throw new IllegalArgumentException("Unknown listening connector name: " + name);
    }

//    All

    public static Connector connector(String name) {
        return connector(Bootstrap.virtualMachineManager(), name);
    }

    public static Connector connector(VirtualMachineManager vmManager, String name) {
        for (Connector connector : vmManager.allConnectors()) {
            if (name.equals(connector.name())) {
                return connector;
            }
        }
        throw new IllegalArgumentException("Unknown connector name: " + name);
    }

//    Default

    public static Connector defaultConnector() {
        return Bootstrap.virtualMachineManager().defaultConnector();
    }

    public static Connector defaultConnector(VirtualMachineManager vmManager) {
        return vmManager.defaultConnector();
    }
}
