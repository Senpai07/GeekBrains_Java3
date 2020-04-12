package lesson6;

import org.junit.Before;
import org.junit.Test;

public class ArrayMethodsThrowExceptionTest {

    private static ArrayMethods method;

    @Before
    public void setUp() {
        arrayWithoutFour = new int[]{1, 2, 3, 5};
        method = new ArrayMethods();
    }

    private int[] arrayWithoutFour;

    @Test(expected = RuntimeException.class)
    public void testThrowException() {
        method.cutArrayAfterLastFour(arrayWithoutFour);
    }

}