package lesson6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ArrayMethodFirstTest {

    @Parameterized.Parameters
    public static List<int[][]> data() {
        return Arrays.asList(new int[][][]{
                {{1, 5, 4, 7}, {7}},
                {{4, 5, 4, 6}, {6}},
                {{4, 4, 3, 2}, {3, 2}},
                {{4, 3, 2, 1}, {3, 2, 1}},
        });
    }

    private final int[] arrayIn;
    private final int[] arrayOut;

    public ArrayMethodFirstTest(int[] arrayIn, int[] arrayOut) {
        this.arrayIn = arrayIn;
        this.arrayOut = arrayOut;
    }

    private static ArrayMethods method;

    @Before
    public void setUp() {
        method = new ArrayMethods();
    }

    @Test
    public void cutArrayAfterLastFour() {
        Assert.assertArrayEquals(arrayOut, method.cutArrayAfterLastFour(arrayIn));
    }

}