package lesson5;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private static CyclicBarrier startBarrier;
    private CountDownLatch countDownLatchFinish;
    private CountDownLatch countDownLatchReady;

    static {
        startBarrier = MainClass.startBarrier;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch cdlReady, CountDownLatch cdlFinish) {
        this.race = race;
        this.speed = speed;
        this.countDownLatchReady = cdlReady;
        this.countDownLatchFinish = cdlFinish;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            countDownLatchReady.countDown();
            System.out.println(this.name + " готов");
            startBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final List<Stage> stages = race.getStages();
        int i = 0;
        for (Stage stage : stages) {
            i++;
            stage.go(this, i, race.getStages().size());
        }
        countDownLatchFinish.countDown();
    }
}
