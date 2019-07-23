package pool;

class Processor implements Runnable{
    private int id;

    Processor(int id) {
        this.id = id;
    }
    public void run() {
        System.out.println("Starting: "+id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed: "+id);
    }

}