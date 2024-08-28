package basicAlgorithm.dynamic_programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Package basic_algorithm.dynamic_programming
 * @Date 2024/5/25 9:31
 * @Author gaojie
 * @description: 01背包
 * 有一个指定体积Vb的背包，n种物品，每种物品各一个，都有自己的体积vi和价值Wi
 * 如何组合可以给背包内装入更高价值的物品（物品体积不能超过背包总体积）。
 */
public class ZeroOneBackpack {

    public static void main(String[] args) {
        int maxVolume = 10;
        List<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList,
                new Item(8, 9),
                new Item(3, 2),
                new Item(4, 4),
                new Item(3, 3));
        System.out.println(execute(itemList, maxVolume));

        int maxVolume2 = 8;
        List<Item> itemList2 = new ArrayList<>();
        Collections.addAll(itemList2,
                new Item(2, 3),
                new Item(3, 4),
                new Item(4, 5),
                new Item(5, 6));
        System.out.println(execute(itemList2, maxVolume2));
    }

    /**
     * 使用动态规划获得最大的价值
     *
     * @param itemList  物品
     * @param maxVolume 背包最大体积
     * @return 最大价值
     */
    public static int execute(List<Item> itemList, int maxVolume) {
        // 初始化dp表，第一列初始赋值为0（体积为0什么都放不下），第一行初始赋值为0（没有物品默认为0）。
        int[][] dpTable = new int[itemList.size() + 1][maxVolume + 1];
        int[][] dpTableTrance = new int[itemList.size() + 1][maxVolume + 1];

        // 填表
        for (int i = 1; i < itemList.size() + 1; i++) {
            for (int j = 1; j < maxVolume + 1; j++) {
                // 当前背包体积 < 当前物品体积，即放不下。
                // itemList.get(i - 1) 是因为物品List从下标0开始，不是从1开始
                if (j < itemList.get(i - 1).getVolume()) {
                    // 放不下当前物品，那么当前情况下最大价值为 前i-1个物品的在该体积下的最大价值
                    dpTable[i][j] = dpTable[i - 1][j];

                } else {
                    // 当前背包体积 > 当前物品体积，可以放得下
                    // 当前容量下，只有两个选择，放入当前物品，不放入当前物品
                    //      不放入当前物品：最大价值为当前体积下前i-1个物品的最大值
                    //      放入当前物品：最大价值为前i-1个物品在背包体积为（当前体积-当前物品体积）的最大值
                    dpTable[i][j] = Math.max(dpTable[i - 1][j],
                            dpTable[i - 1][j - itemList.get(i - 1).getVolume()] + itemList.get(i - 1).getValue());
                }
            }
        }
        // 输出dp表
        for (int i = 0; i < itemList.size() + 1; i++) {
            for (int j = 0; j < maxVolume + 1; j++) {
                System.out.print(dpTable[i][j] + " ");
            }
            System.out.println();
        }
        return dpTable[itemList.size()][maxVolume];
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