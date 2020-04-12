package lesson1.task3;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private final ArrayList<T> box;

    public Box() {
        this.box = new ArrayList<>();
    }

    public void addToBox(T fruit) {
        box.add(fruit);
    }

    public float getWeight() {
        float weightOfBox = 0;
        for (T t : this.box) {
            weightOfBox += t.getWeightOneFruit();
        }
        return weightOfBox;
    }

    public boolean compare(Box<?> box2) {
        if (this == box2) {
            return true;
        } else return this.getWeight() == box2.getWeight();
    }

    public void MoveTo(Box<T> box2) {
        for (T t : this.box) {
            box2.addToBox(t);
        }
        this.box.clear();
    }
}
