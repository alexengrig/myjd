package dev.alexengrig.myjdi.pretty;

import com.sun.jdi.connect.Connector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ConnectorPrettier implements Prettier<Connector> {
    protected static final Map<Class<? extends Connector.Argument>, ArgumentPrettier<? extends Connector.Argument>> DEFAULT_ARGUMENT_PRETTIERS;

    static {
        final Map<Class<? extends Connector.Argument>, ArgumentPrettier<? extends Connector.Argument>> map = new HashMap<>();
        map.put(Connector.Argument.class, new ArgumentPrettier<>());
        map.put(Connector.BooleanArgument.class, new BooleanArgumentPrettier());
        map.put(Connector.IntegerArgument.class, new IntegerArgumentPrettier());
        map.put(Connector.StringArgument.class, new StringArgumentPrettier());
        map.put(Connector.SelectedArgument.class, new SelectedArgumentPrettier());
        DEFAULT_ARGUMENT_PRETTIERS = map;
    }

    protected StringJoiner joiner;

    @Override
    public String pretty(Connector connector) {
        joiner = new StringJoiner(getDelimiter());
        addField(1, "Class", connector.getClass().getName());
        addField(1, "Name", connector.name());
        addField(1, "Description", connector.description());
        addField(1, "Transport name", connector.transport().name());
        addArguments(connector.defaultArguments());
        return joiner.toString();
    }

    protected String getDelimiter() {
        return System.lineSeparator();
    }

    protected void add(String value) {
        joiner.add(value);
    }

    protected void addHeader(int level, String name) {
        add(String.format("%s%s:", String.join("", Collections.nCopies(level, "\t")), name));
    }

    protected void addField(int level, String name, Object value) {
        add(String.format("%s%s: %s.", String.join("", Collections.nCopies(level, "\t")), name, value));
    }

    protected void addArguments(Map<String, Connector.Argument> arguments) {
        if (arguments.isEmpty()) {
            addField(1, "Arguments", "no");
        } else {
            addHeader(1, "Arguments");
            for (Connector.Argument argument : arguments.values()) {
                addArgument(argument);
            }
        }
    }

    protected void addArgument(Connector.Argument argument) {
        addHeader(2, argument.name());
        final ArgumentPrettier<Connector.Argument> prettier = getArgumentPrettier(argument);
        add(prettier.pretty(argument));
    }

    @SuppressWarnings("unchecked")
    protected ArgumentPrettier<Connector.Argument> getArgumentPrettier(Connector.Argument argument) {
        final ArgumentPrettier<?> prettier;
        final Map<Class<? extends Connector.Argument>, ArgumentPrettier<? extends Connector.Argument>> map = getArgumentPrettiers();
        if (argument instanceof Connector.BooleanArgument) {
            prettier = map.get(Connector.BooleanArgument.class);
        } else if (argument instanceof Connector.IntegerArgument) {
            prettier = map.get(Connector.IntegerArgument.class);
        } else if (argument instanceof Connector.StringArgument) {
            prettier = map.get(Connector.StringArgument.class);
        } else if (argument instanceof Connector.SelectedArgument) {
            prettier = map.get(Connector.SelectedArgument.class);
        } else {
            prettier = map.get(Connector.Argument.class);
        }
        return (ArgumentPrettier<Connector.Argument>) prettier;
    }

    protected Map<Class<? extends Connector.Argument>, ArgumentPrettier<? extends Connector.Argument>> getArgumentPrettiers() {
        return DEFAULT_ARGUMENT_PRETTIERS;
    }

    protected static class ArgumentPrettier<T extends Connector.Argument> implements Prettier<T> {
        protected StringJoiner joiner;

        @Override
        public String pretty(T argument) {
            joiner = new StringJoiner(getDelimiter());
            addField(3, "Class", argument.getClass().getName());
            addField(3, "Label", argument.label());
            addField(3, "Description", argument.description());
            addField(3, "Value", argument.value());
            addField(3, "Specifiable", argument.mustSpecify());
            addSpecificFields(argument);
            return joiner.toString();
        }

        protected String getDelimiter() {
            return System.lineSeparator();
        }

        protected void addField(int level, String name, Object value) {
            joiner.add(String.format("%s%s: %s.", String.join("", Collections.nCopies(level, "\t")), name, value));
        }

        protected void addSpecificFields(T argument) {
        }
    }

    protected static class BooleanArgumentPrettier extends ArgumentPrettier<Connector.BooleanArgument> {
    }

    protected static class IntegerArgumentPrettier extends ArgumentPrettier<Connector.IntegerArgument> {
        @Override
        protected void addSpecificFields(Connector.IntegerArgument argument) {
            addField(3, "Min", argument.min());
            addField(3, "Max", argument.max());
        }
    }

    protected static class StringArgumentPrettier extends ArgumentPrettier<Connector.StringArgument> {
    }

    protected static class SelectedArgumentPrettier extends ArgumentPrettier<Connector.SelectedArgument> {
        @Override
        protected void addSpecificFields(Connector.SelectedArgument argument) {
            addField(3, "Choices", argument.choices());
        }
    }
}
