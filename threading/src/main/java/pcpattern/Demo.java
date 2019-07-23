package pcpattern;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        BlockingDeque<PCData> queue = new LinkedBlockingDeque<>(10);
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);

        ExecutorService pool = Executors.newCachedThreadPool();

        pool.execute(producer1);
        pool.execute(producer2);
        pool.execute(producer3);
        pool.execute(consumer1);
        pool.execute(consumer2);
        pool.execute(consumer3);

        Thread.sleep(10 * 1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();
        Thread.sleep(3000);
        pool.shutdown();
    }
}
