package basicAlgorithm.branch_bound;

public class Item {
    /**
     * 体积
     */
    int volume;
    /**
     * 价值
     */
    int value;

    public double getValuePerVolume() {
        return value * 1.0 / volume;
    }

    public Item(int volume, int value) {
        this.volume = volume;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Item{" +
                "volume=" + volume +
                ", value=" + value +
                '}';
    }
}
