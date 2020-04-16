package lesson7.handler;

import lesson7.annotations.AfterSuite;
import lesson7.annotations.BeforeSuite;
import lesson7.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GoTest {

    public static final String CHECK_ERROR = "Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре";

    public static void start(Class<?> testedClass) {
        int cntBeforeAnnotations = 0;
        int cntAfterAnnotations = 0;

//        Map<Integer, Method> methodMap = new TreeMap<>();

        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> testMethodList = new ArrayList<>();

        Method[] methods = testedClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeSuiteMethod = method;
                cntBeforeAnnotations++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                afterSuiteMethod = method;
                cntAfterAnnotations++;
            }
            if (method.getAnnotation(Test.class) != null) {
                testMethodList.add(method);
            }
        }

        if ((cntBeforeAnnotations > 1) || (cntAfterAnnotations > 1)) {
            throw new RuntimeException(CHECK_ERROR);
        }

        testMethodList.sort(Comparator.comparing(method -> method.getAnnotation(Test.class).priority()));

        if (beforeSuiteMethod != null) {
            testMethodList.add(0, beforeSuiteMethod);
        }
        if (afterSuiteMethod != null) {
            testMethodList.add(afterSuiteMethod);
        }

        Object testClass = null;
        try {
            testClass = testedClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Method method : testMethodList) {
            if (Modifier.isPrivate(method.getModifiers())) {
                method.setAccessible(true);
            }
            try {
                method.invoke(testClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

