package lesson4;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorServiceExamples {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("TASK 1");
        Callable<String> task = () -> Thread.currentThread().getName();
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            Future<String> result = service.submit(task);
            System.out.println(result.get());
        }
        service.shutdown();

        System.out.println("TASK 2");
        Object lock = new Object();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<String> task2 = () -> {
            System.out.println(Thread.currentThread().getName());
            lock.wait(2000);
            System.out.println("Finished");
            return "result";
        };
        for (int i = 0; i < 5; i++) {
            executorService.submit(task2);
        }
        executorService.shutdown();

        System.out.println("TASK 3");
        Lock lock3 = new ReentrantLock();
        Runnable task3 = () -> {
            lock3.lock();
            Thread thread = Thread.currentThread();
            System.out.println("Hello from " + thread.getName());
            lock3.unlock();
        };
        Thread thread = new Thread(task3);
        thread.start();

        System.out.println("TASK 4");
        Semaphore semaphore = new Semaphore(0);
        Runnable task4 = () -> {
            try {
                semaphore.acquire();
                System.out.println("Finished");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task4).start();
        Thread.sleep(3000);
        semaphore.release(1);

        System.out.println("TASK 5");
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Runnable task5 = () -> {
            try {
                countDownLatch.countDown();
                System.out.println("Countdown: " + countDownLatch.getCount());
                countDownLatch.await();
                System.out.println("Finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(task5).start();
        }

        System.out.println("TASK 6");
        Runnable action = () -> System.out.println("На старт!");
        CyclicBarrier barrier = new CyclicBarrier(3, action);
        Runnable task6 = () -> {
            try {
                barrier.await();
                System.out.println("Finished");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        System.out.println("Limit: " + barrier.getParties());
        for (int i = 0; i < 3; i++) {
            new Thread(task6).start();
        }

        System.out.println("TASK 7");
        Exchanger<String> exchanger = new Exchanger<>();
        Runnable task7 = () -> {
            try {
                Thread thread7 = Thread.currentThread();
                String withThreadName = exchanger.exchange(thread7.getName());
                System.out.println(thread7.getName() + " обменялся с " + withThreadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task7).start();
        new Thread(task7).start();

        System.out.println("TASK 8");
        Phaser phaser = new Phaser();
        // Вызывая метод register, мы регистрируем текущий поток (main) как участника
        phaser.register();
        System.out.println("Phase count is " + phaser.getPhase());
        testPhase(phaser);
        testPhase(phaser);
        testPhase(phaser);
        // Через 3 секунды прибываем к барьеру и снимаемся регистрацию. Кол-во прибывших = кол-во регистраций = пуск
        Thread.sleep(3000);
        phaser.arriveAndDeregister();
        System.out.println("Phase count is " + phaser.getPhase());

    }

    private static void testPhase(final Phaser phaser) {
        // Говорим, что будет +1 участник на Phaser
        phaser.register();
        // Запускаем новый поток
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " arrived");
            phaser.arriveAndAwaitAdvance(); //threads register arrival to the phaser.
            System.out.println(name + " after passing barrier");
        }).start();
    }
}
