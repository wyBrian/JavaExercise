package lock;

import java.util.LinkedList;
import java.util.Random;

class Processor {
    private LinkedList<Integer> list = new LinkedList<Integer>();
    private final int LIMIT = 10;
    private final Object lock = new Object();

    void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                //whenever the thread is notified starts again from the loop
                while (list.size() == LIMIT) {
                    lock.wait();// wait() is also true
                }
                list.add(value);

                System.out.println("Producer added: " + value + " queue size is " + list.size());
                value++;
                lock.notify();
            }
        }
    }

    void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }

                int value = list.removeFirst();
                System.out.print("Removed value by consumer is: " + value);
                System.out.println(" Now list size is: " + list.size());
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000)); //force producer fill the queue to LIMIT_SIZE
        }
    }
}
