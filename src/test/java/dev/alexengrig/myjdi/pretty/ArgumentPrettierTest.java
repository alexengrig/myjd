package dev.alexengrig.myjdi.pretty;

import com.sun.jdi.connect.Connector;
import org.junit.Assert;
import org.junit.Test;

public class ArgumentPrettierTest {
    protected ArgumentPrettier prettier = new ArgumentPrettier();
    protected Connector.Argument argument = new Connector.Argument() {
        @Override
        public String name() {
            return "NAME";
        }

        @Override
        public String label() {
            return "LABEL";
        }

        @Override
        public String description() {
            return "DESCRIPTION";
        }

        @Override
        public String value() {
            return "VALUE";
        }

        @Override
        public void setValue(String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isValid(String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean mustSpecify() {
            return true;
        }
    };

    @Test
    public void check() {
        final String expected = "" +
                "Class: dev.alexengrig.myjdi.pretty.ArgumentPrettierTest$1." + System.lineSeparator() +
                "Name: NAME." + System.lineSeparator() +
                "Label: LABEL." + System.lineSeparator() +
                "Description: DESCRIPTION." + System.lineSeparator() +
                "Value: VALUE." + System.lineSeparator() +
                "Specifiable: true.";
        final String actual = prettier.pretty(argument);
        Assert.assertEquals(expected, actual);
    }
}