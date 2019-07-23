package thread;

public class DemoThread1 extends Thread{
    public void run() {
        for(int i=0;i<10;i++) {
            System.out.println("Hello " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        DemoThread1 t1 = new DemoThread1();
        t1.start();
        DemoThread1 t2 = new DemoThread1();
        t2.start();
    }
}
