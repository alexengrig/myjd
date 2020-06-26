package dev.alexengrig.myjdi.connection;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

import java.io.IOException;
import java.util.Map;

public class MyLaunchConnector extends ConnectorDelegate<LaunchingConnector> {
    protected final Launcher launcher;

    public MyLaunchConnector(LaunchingConnector connector) {
        this(connector, Launchers.launcher(connector));
    }

    public MyLaunchConnector(LaunchingConnector connector, Map<String, Argument> arguments) {
        this(connector, Launchers.launcher(connector, arguments));
    }

    protected MyLaunchConnector(LaunchingConnector connector, Launcher launcher) {
        super(connector);
        this.launcher = launcher;
    }

    @Override
    public VirtualMachine connect() throws IOException, IllegalConnectorArgumentsException, VMStartException {
        return launcher.launch();
    }
}
