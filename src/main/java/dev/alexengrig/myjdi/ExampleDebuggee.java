package dev.alexengrig.myjdi;

public class ExampleDebuggee {
    public static void main(String[] args) {
        String text = "Some text";
        int age = 30;
        method();
        throwException();
    }

    private static void method() {
        String string = "This is STRING!!1";
    }

    private static void throwException() {
        throw new RuntimeException("This is EXCEPTION!!1");
    }
}
