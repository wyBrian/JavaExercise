package processor;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Processor proc1 = new Processor();
        proc1.start();

        System.out.println("Press return to stop ...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        proc1.shutdown();


    }
}

class Processor extends Thread{
    // Using volatile to prevent thread from caching variable
    private volatile boolean running = true;
    public void run() {
        while(running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void shutdown() {
        running = false;
    }
}