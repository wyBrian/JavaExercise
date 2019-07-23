package pcpattern;

public class PCData {
    private final int intData;

    public PCData(int d) {
        intData = d;
    }

    public PCData(String stringData) {
        intData = Integer.parseInt(stringData);
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "Data : " + intData;
    }
}
