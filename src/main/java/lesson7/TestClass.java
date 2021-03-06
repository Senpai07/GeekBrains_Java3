package lesson7;

import lesson7.annotations.AfterSuite;
import lesson7.annotations.BeforeSuite;
import lesson7.annotations.Test;

public class TestClass {

    @BeforeSuite
    public void beforeSuiteMethod() {
        System.out.println("BeforeSuite method");
    }

    @Test(priority = 7)
    public void testMethod1() {
        System.out.println("Test method priority = 7");
    }

    @Test(priority = 5)
    public void testMethod2() {
        System.out.println("Test method1 priority = 5");
    }

    @Test(priority = 10)
    public void testMethod3() {
        System.out.println("Test method priority = 10");
    }

    @Test(priority = 3)
    private void testMethod4() {
        System.out.println("Test private method priority = 3");
    }

    @Test
    public void testMethod5() {
        System.out.println("Test method priority = default(1)");
    }

    @Test(priority = 5)
    public void testMethod6() {
        System.out.println("Test method2 priority = 5");
    }

    @AfterSuite
    public void afterSuiteMethod() {
        System.out.println("AfterSuite method");
    }

//    @BeforeSuite
//    public void beforeSuiteMethod2() {
//        System.out.println("BeforeSuite method 2");
//    }
}
