package dev.alexengrig.myjdi;

public class ExampleDebuggee implements Runnable {
    private final String stringField = "This is FIELD!!1";
    private int intFiled = 20;

    public static void main(String[] args) {
        final ExampleDebuggee debuggee = new ExampleDebuggee();
        debuggee.run();
    }

    private static void staticMethod() {
        String string = "This is STRING!!1";
    }

    private static void throwException() {
        throw new RuntimeException("This is EXCEPTION!!1");
    }

    @Override
    public void run() {
        String text = stringField;
        int age = 30;
        intFiled = 25;
        method();
        staticMethod();
        throwException();
    }

    private void method() {
    }
}
