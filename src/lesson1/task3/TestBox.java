package lesson1.task3;

public class TestBox {
    public static void main(String[] args) {
        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple(2.5f);
        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        Orange orange3 = new Orange();
        Orange orange4 = new Orange(2.3f);
        Orange orange5 = new Orange(3.7f);

        Box<Orange> orangeBox1 = new Box<>();
        Box<Orange> orangeBox2 = new Box<>();
        Box<Apple> appleBox = new Box<>();

        System.out.println("Заполняем апельсинами 2 коробки");
        orangeBox1.addToBox(orange1);
        orangeBox1.addToBox(orange2);
        orangeBox1.addToBox(orange3);
        orangeBox2.addToBox(orange4);
        orangeBox2.addToBox(orange5);

        System.out.print("> Вес коробки апельсинов №1: ");
        System.out.println(orangeBox1.getWeight());
        System.out.print("> Вес коробки апельсинов №2: ");
        System.out.println(orangeBox2.getWeight());

        System.out.println("Заполняем яблоками 1 коробку");
        appleBox.addToBox(apple1);
        appleBox.addToBox(apple2);
        appleBox.addToBox(apple3);

        System.out.print("> Вес коробки яблок: ");
        System.out.println(appleBox.getWeight());

        System.out.print("> Равны ли по весу коробки апельсинов?: ");
        System.out.println(orangeBox1.compare(orangeBox2));

        System.out.print("> Равны ли по весу коробка апельсинов №1 и коробка яблок?: ");
        System.out.println(orangeBox1.compare(appleBox));

        System.out.println("Пересыпаем апельсины из коробки №1 в коробку №2");
        orangeBox1.MoveTo(orangeBox2);
        System.out.print("> Вес коробки апельсинов №1: ");
        System.out.println(orangeBox1.getWeight());
        System.out.print("> Вес коробки2 апельсинов №2: ");
        System.out.println(orangeBox2.getWeight());
    }
}
