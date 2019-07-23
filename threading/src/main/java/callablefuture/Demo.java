package callablefuture;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        //anonymous call of Callable
        Future<Integer> future = executor.submit(() -> {

            //return value is Integer
            Random random = new Random();
            int duration = random.nextInt(4000);
            if (duration > 2000) {
                throw new TimeoutException ("Sleeping for too long.");
            }

            System.out.println("Starting ...");
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ignored) {}
            System.out.println("Finished.");
            return duration;

        });

        executor.shutdown();
//        executor.awaitTermination(1, TimeUnit.DAYS);
        try {
            System.out.println("Result is: " + future.get());

        } catch (InterruptedException ignored) {
        } catch (ExecutionException e) {
            TimeoutException ex = (TimeoutException) e.getCause();
            System.out.println(ex.getMessage());
        }
    }
}
