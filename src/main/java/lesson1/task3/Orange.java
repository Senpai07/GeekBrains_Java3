package lesson1.task3;

public class Orange extends Fruit {

    public static final float WEIGHT = 1.5f;

    public Orange(float weight) {
        this.weightOneFruit = weight;
    }

    public Orange() {
        this(WEIGHT);
    }

}
