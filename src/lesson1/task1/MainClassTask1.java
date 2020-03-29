package lesson1.task1;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainClassTask1 {

    static <T> void swapTwoNumbersInArray(T[] array, int firstPos, int secondPos) throws ArrayIndexOutOfBoundsException {
        if (firstPos < 0 || firstPos > array.length ||
                secondPos < 0 || secondPos > array.length || firstPos == secondPos) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Неправильно указаны индексы элементов!%nЗначения должны быть в пределах 0..%d%n",
                            array.length - 1));
        }
        T value = array[firstPos];
        array[firstPos] = array[secondPos];
        array[secondPos] = value;
    }

    public static void main(String[] args) {
        // Integer array
        Integer[] nums = {1, 2, 3, 4, 5};
        System.out.println("before: " + Arrays.toString(nums));
        swapTwoNumbersInArray(nums, 0, 4);
        System.out.println("after: " + Arrays.toString(nums));
        // String array
        String[] strings = {"one", "two", "three", "four", "five"};
        System.out.println("before: " + Arrays.toString(strings));
        swapTwoNumbersInArray(strings, 0, 2);
        System.out.println("after: " + Arrays.toString(strings));
    }

}
