package pcpattern;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

public class Consumer implements Runnable{

    private BlockingDeque<PCData> queue;
    private final static int SLEEPTIME = 1000;

    public Consumer(BlockingDeque<PCData> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        System.out.println("Start consumer id = "+ Thread.currentThread().getId());
        Random r = new Random();
        try {
            while(true) {
                PCData data = queue.take();
                int re = data.getIntData() * data.getIntData();
                System.out.println(MessageFormat.format("{0}*{1}={2}", data.getIntData(), data.getIntData(), re));
                Thread.sleep(r.nextInt(SLEEPTIME));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
