package lesson6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ArrayMethodsSecondTest {

    public ArrayMethodsSecondTest(int[] arrayIn, boolean result) {
        this.arrayIn = arrayIn;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3}, true},
                {new int[]{2, 3, 4, 5}, true},
                {new int[]{5, 6, 7, 8}, false},
                {new int[]{5, 5, 14, 5}, false},
        });
    }

    private final int[] arrayIn;
    private final boolean result;
    private static ArrayMethods method;

    @Before
    public void setUp() {
        method = new ArrayMethods();
    }

    @Test
    public void isArrayContainsOneOrFour() {
        Assert.assertEquals(result, method.isArrayContainsOneOrFour(arrayIn));
    }
}