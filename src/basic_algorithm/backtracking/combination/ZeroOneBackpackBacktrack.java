package basic_algorithm.backtracking.combination;

/**
 * @author: gj
 * @date: 2024/6/5 14:54
 * @description: 回溯法解决01背包
 */
public class ZeroOneBackpackBacktrack {
    private static int maxValue = 0;

    public static void main(String[] args) {
        int maxVolume = 10;
        Item[] items = new Item[]{
                new Item(8, 9),
                new Item(3, 2),
                new Item(4, 4),
                new Item(3, 3)};
        execute(items, maxVolume);
        System.out.println(maxValue);

        int maxVolume2 = 8;
        maxValue = 0;
        Item[] items2 = new Item[]{
                new Item(2, 3),
                new Item(3, 4),
                new Item(4, 5),
                new Item(5, 6)};
        execute(items2, maxVolume2);
        System.out.println(maxValue);
    }

    public static void execute(Item[] items, int maxVolume) {
        zeroOneBackpackBacktrack(items, maxVolume, 0, 0, 0);
    }

    public static void zeroOneBackpackBacktrack(Item[] items, int maxVolume, int index, int currentVolume, int currentValue) {
        // 当前体积已经超出背包最大体积
        if (currentVolume > maxVolume) return;
        // 更新最大价值
        maxValue = Math.max(currentValue, maxValue);
        // 未到达最后一个物品
        if (index < items.length) {
            // 放入当前物品
            zeroOneBackpackBacktrack(items, maxVolume, index + 1, currentVolume + items[index].getVolume(), currentValue + items[index].getValue());
            // 不放入当前物品
            zeroOneBackpackBacktrack(items, maxVolume, index + 1, currentVolume, currentValue);
        }
    }


}

class Item {
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
