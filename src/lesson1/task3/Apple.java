package lesson1.task3;

public class Apple extends Fruit {

    public static final float WEIGHT = 1.0f;

    public Apple(float weight) {
        this.weightOneFruit = weight;
    }

    public Apple() {
        this(WEIGHT);
    }
}
