package dev.alexengrig.myjdi.service;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import java.util.Map;

public class LaunchingConnectorService {
    public LaunchingConnector connect() {
        VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
        return vmManager.defaultConnector();
    }

    public Map<String, Connector.Argument> arguments(Connector connector, Map<String, String> arguments) {
        final Map<String, Connector.Argument> defaultArguments = connector.defaultArguments();
        for (String name : arguments.keySet()) {
            defaultArguments.get(name).setValue(arguments.get(name));
        }
        return defaultArguments;
    }
}
