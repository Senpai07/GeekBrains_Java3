package lesson7;

import lesson7.handler.GoTest;

public class Main {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        GoTest.start(testClass.getClass());
    }

}
