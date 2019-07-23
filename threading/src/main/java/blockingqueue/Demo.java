package blockingqueue;

import java.util.Random;

@SuppressWarnings("InfiniteLoopStatement")
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(10);
        final Random random = new Random();
        Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    blockingQueue.put(random.nextInt(10));
                }
            } catch (InterruptedException ignored) {}
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);//wait for putting to the queue first
            } catch (InterruptedException ex) {
                System.out.println("Exception " + ex.getMessage());
            }
            try {
                while (true) {
                    blockingQueue.take();
                }
            } catch (InterruptedException ignored) {}
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
