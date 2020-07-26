package dev.alexengrig.myjdi.connect;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.io.IOException;
import java.util.Map;

public class YouthLaunchConnector extends YouthConnectorDelegate<LaunchingConnector> {
    protected final YouthLauncher launcher;

    public YouthLaunchConnector(LaunchingConnector connector) {
        this(connector, YouthLaunchers.launcher(connector));
    }

    public YouthLaunchConnector(LaunchingConnector connector, Map<String, Argument> arguments) {
        this(connector, YouthLaunchers.launcher(connector, arguments));
    }

    protected YouthLaunchConnector(LaunchingConnector connector, YouthLauncher launcher) {
        super(connector);
        this.launcher = launcher;
    }

    @Override
    public YouthVirtualMachine connect() throws IOException, IllegalConnectorArgumentsException, VMStartException {
        return launcher.launch();
    }
}
