package dev.alexengrig.myjd;

public class ExampleDebuggee {
    public static void main(String[] args) {
        String text = "Some text";
        int age = 30;
        method();
    }

    private static void method() {
        String string = "This is STRING!!1";
    }
}
