package lesson1.task2;

import java.util.ArrayList;
import java.util.Arrays;

public class MainClassTask2 {
    private static <T> ArrayList<?> arrayToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    public static void main(String[] args) {
        // Integer array
        Integer[] nums = {1, 2, 3, 4, 5};
        ArrayList<?> resIntegerArray = arrayToArrayList(nums);
        System.out.println("Integer ArrayList: " + resIntegerArray.toString());
        // String array
        String[] strings = {"one", "two", "three", "four", "five"};
        ArrayList<?> resStringArray = arrayToArrayList(strings);
        System.out.println("String ArrayList: " + resStringArray.toString());

    }
}
