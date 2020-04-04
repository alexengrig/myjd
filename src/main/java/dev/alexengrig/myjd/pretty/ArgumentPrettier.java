package dev.alexengrig.myjd.pretty;

import com.sun.jdi.connect.Connector;

import java.util.StringJoiner;

public class ArgumentPrettier implements Prettier<Connector.Argument> {
    @Override
    public String pretty(Connector.Argument argument) {
        final StringJoiner joiner = new StringJoiner(getSeparator());
        joiner.add(prettyEach(prettyClass(argument.getClass())));
        joiner.add(prettyEach(prettyName(argument.name())));
        joiner.add(prettyEach(prettyLabel(argument.label())));
        joiner.add(prettyEach(prettyDescription(argument.description())));
        joiner.add(prettyEach(prettyValue(argument.value())));
        joiner.add(prettyEach(prettySpecifiable(argument.mustSpecify())));
        return joiner.toString();
    }

    protected String getSeparator() {
        return System.lineSeparator();
    }

    protected String prettyEach(String line) {
        return line;
    }

    protected String prettyClass(Class<?> clazz) {
        return String.format("Class: %s.", clazz.getName());
    }

    protected String prettyName(String name) {
        return String.format("Name: %s.", name);
    }

    protected String prettyLabel(String label) {
        return String.format("Label: %s.", label);
    }

    protected String prettyDescription(String description) {
        return String.format("Description: %s.", description);
    }

    protected String prettyValue(String value) {
        return String.format("Value: %s.", value);
    }

    protected String prettySpecifiable(boolean specifiable) {
        return String.format("Specifiable: %s.", specifiable);
    }
}
