package lesson4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainVariant3 {
    private final int Count = 3;
    ExecutorService executorService;

    public MainVariant3() {

        executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            CountDownLatch cdl1 = new CountDownLatch(3);
            executorService.execute(new MyThread(cdl1, "A"));
            executorService.execute(new MyThread(cdl1, "B"));
            executorService.execute(new MyThread(cdl1, "C"));
        }

        executorService.shutdown();
    }

    static class MyThread implements Runnable {
        String name;
        CountDownLatch latch;

        public MyThread(CountDownLatch latch, String name) {
            this.name = name;
            this.latch = latch;
            new Thread(this);
        }

        public void run() {
            while (true) {
                if ((name.equals("A")) && (latch.getCount() == 3)) {
                    break;
                } else if
                ((name.equals("B")) && (latch.getCount() == 2)) {
                    break;
                } else if
                ((name.equals("C")) && (latch.getCount() == 1)) {
                    break;
                }
            }
            System.out.println(name);
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (name.equals("C")) System.out.println("---");
        }
    }

    public static void main(String[] args) {
        new MainVariant3();
    }
}
