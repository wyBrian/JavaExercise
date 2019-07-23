package waitnotify;

import java.util.Scanner;

class Processor {

    void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ....");
            wait();
            System.out.println("Resumed.");
        }
    }

    void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            scanner.close();
            notify();
            Thread.sleep(5000);
            System.out.println("Consumption done.");
        }
    }

}
