package basic_algorithm.branch_bound;

public class Node {
    int index;
    int currentVolume;
    int currentValue;
    String items = "";
    double upperBound;

    public Node() {
    }

    public Node(int index, int currentVolume, int currentValue, String items, double upperBound) {
        this.index = index;
        this.currentVolume = currentVolume;
        this.currentValue = currentValue;
        this.items = items;
        this.upperBound = upperBound;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    public String toString() {
        return "取物品：" + items + ";" + "最大值：" + currentValue;
    }
}
