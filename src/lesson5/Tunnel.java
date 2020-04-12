package lesson5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static Semaphore semaphore = new Semaphore(MainClass.CARS_COUNT / 2);

    public Tunnel(int length) {
        this.length = length;
        this.description = "Тоннель " + this.length + " метров";
    }

    @Override
    public void go(Car c, int stagePos, int stageCount) {
        try {
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
            semaphore.acquire();
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(c.getName() + " закончил этап: " + description);
            if (stagePos == stageCount && !MainClass.hasWinner) {
                MainClass.hasWinner = true;
                System.out.println(c.getName() + " WIN!!!");
            }
        }
    }
}
