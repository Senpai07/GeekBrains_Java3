package lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;
    static final CountDownLatch countDownLatchReady = new CountDownLatch(CARS_COUNT);
    static final CountDownLatch countDownLatchFinish = new CountDownLatch(CARS_COUNT);
    static final CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT);
    public static boolean hasWinner = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(20), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            final int speed = 20 + (int) (Math.random() * 10);
            cars[i] = new Car(race, speed, countDownLatchReady, countDownLatchFinish);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        countDownLatchReady.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        countDownLatchFinish.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
