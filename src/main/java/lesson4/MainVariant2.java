package lesson4;

public class MainVariant2 {
    volatile int status = 0;

    public static void main(String[] args) {
        MainVariant2 lock = new MainVariant2();
        Thread t1 = new Thread(new Task("A", lock, 1));
        Thread t2 = new Thread(new Task("B", lock, 2));
        Thread t3 = new Thread(new Task("C", lock, 3));
        t1.start();
        t2.start();
        t3.start();
    }
}

class Task implements Runnable {

    private final String message;
    private final MainVariant2 lock;
    private final int p;

    Task(String text, MainVariant2 obj, int p) {
        message = text;
        this.lock = obj;
        this.p = p;
    }

    @Override
    public void run() {

        while (lock.status < 13) {
            synchronized (lock) {

                while (!((lock.status % 3) == 0) && p == 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (!((lock.status % 3) == 1) && p == 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (!((lock.status % 3) == 2) && p == 3) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(message);
                lock.status++;
                if ((lock.status % 3) == 0) System.out.println("---");
                lock.notifyAll();
            }
        }
    }
}
