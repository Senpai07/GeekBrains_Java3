package lesson4;

public class MainVariant1 {

    private static final Object mon = new Object();

    public static void main(String[] args) {

        new Thread(() -> { // t1
            synchronized (mon) {
                try {
                    System.out.println("A"); // выполнили
                    mon.wait(); // t1 уснул
                    System.out.println("A"); // выполнили
                    mon.wait(); // t1 уснул
                    System.out.println("A"); // выполнили
                    mon.wait(); // t1 уснул
                    System.out.println("A"); // выполнили
                    mon.wait(); // t1 уснул
                    System.out.println("A"); // выполнили
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> { // t2
            synchronized (mon) {
                try {
                    System.out.println("B"); // выполнили
                    mon.wait(); // t2 уснул
                    System.out.println("B"); // выполнили
                    mon.notify(); // разбудили t3
                    mon.wait(); // t2 уснул
                    System.out.println("B"); // выполнили
                    mon.notify(); // разбудили t3
                    mon.wait(); // t2 уснул
                    System.out.println("B"); // выполнили
                    mon.notify(); // разбудили t3
                    mon.wait(); // t2 уснул
                    System.out.println("B"); // выполнили
                    mon.notify(); // разбудили t3
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> { // t3
            synchronized (mon) {
                try {
                    System.out.println("C"); // выполнили
                    System.out.println("---");
                    mon.notifyAll(); // разбудили t1 и t2
                    mon.wait(); // уснул t3
                    System.out.println("C"); // выполнили
                    System.out.println("---");
                    mon.notifyAll(); // разбудили t1 и t2
                    mon.wait(); // уснул t3
                    System.out.println("C"); // выполнили
                    System.out.println("---");
                    mon.notifyAll(); // разбудили t1 и t2
                    mon.wait(); // уснул t3
                    System.out.println("C"); // выполнили
                    System.out.println("---");
                    mon.notifyAll(); // разбудили t1 и t2
                    mon.wait(); // уснул t3
                    System.out.println("C"); // выполнили
                    System.out.println("---"); //
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
