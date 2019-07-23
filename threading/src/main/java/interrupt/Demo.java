package interrupt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting.");

        ExecutorService executor = Executors.newCachedThreadPool();

        Future<?> future = executor.submit(() -> {
            for (int i = 0; i < 1E8; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.printf("Interrupted at %d !!!", i);
                    break;
                }
            }

            return null;
        });

//        executor.shutdown();
        Thread.sleep(500);

        /*
        in this example, there are different ways you can interrupt a thread
        execution.
         */

        //JavaDoc: http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html#cancel-boolean-
        future.cancel(true);

        //JavaDoc: http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html#shutdownNow--
        executor.shutdownNow();

        executor.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Finished.");
    }
}
