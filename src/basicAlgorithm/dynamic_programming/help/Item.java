package basicAlgorithm.dynamic_programming.help;

public class Item {
    /**
     * 体积
     */
    int volume;
    /**
     * 价值
     */
    int value;

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
}