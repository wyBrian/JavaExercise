package thread;

public class DemoThread2 implements Runnable{

    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Hello "+i);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new DemoThread2());
        Thread t2 = new Thread(new DemoThread2());
        t1.start();
        t2.start();
    }

}
