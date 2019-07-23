package disruptor;

public class PCData {
    private long value;

    public void set(long v){
        value = v;
    }

    public long get() {
        return value;
    }
}