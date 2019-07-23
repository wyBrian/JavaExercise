package sync;

public class Demo {
    private int count = 0;

    private synchronized void increment() {
        count++;
    }

    public static void main(String[] args) {
        Demo app = new Demo();
        app.doWork();
    }

    private void doWork() {
        Thread t1 = new Thread(() -> {
            for(int i=0; i<10000; i++) {
                increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for(int i=0; i<10000; i++) {
                increment();
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Count is : " + count);
    }
}
