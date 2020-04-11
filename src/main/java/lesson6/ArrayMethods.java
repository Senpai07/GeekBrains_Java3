package lesson6;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class ArrayMethods {

    public int[] cutArrayAfterLastFour(int[] array) {
        int[] cutArray = ArrayUtils.subarray(array, ArrayUtils.lastIndexOf(array, 4) + 1,
                ArrayUtils.getLength(array) + 1);
        if (Arrays.equals(array, cutArray)) throw new RuntimeException();
        return cutArray;
    }

    public boolean isArrayContainsOneOrFour(int[] array) {
        return (ArrayUtils.contains(array, 1) || ArrayUtils.contains(array, 4));
    }

}
