package basicAlgorithm.dynamic_programming;

import basicAlgorithm.dynamic_programming.help.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 完全背包
 */
public class UnboundedKnapsack {

    public static void main(String[] args) {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(2, 3));
        itemList.add(new Item(3, 4));
        itemList.add(new Item(4, 5));

        int maxVolume = 7;
        int maxValue = execute(itemList, maxVolume);
        System.out.println("最大价值为：" + maxValue);
    }

    /**
     * 完全背包，这里直接采用滚动数组优化
     * 数组dp[i]标识容量为i时，已经遍历过的所有物品组合的最大价值（对应二维数组解释dp[i][j]，容量为j时，前i个物品组合的最大价值）
     * 在遍历某个物品时，容量为i时的最优解，有两个选择：
     * - 不选当前物品
     * - 选当前物品，而当前物品可以选多次，通过取 dp[i - 当前物品质量]的最优解 + 当前物品价值，而在dp[i-物品质量]处，可能已经选过物品i了
     * 二者取最优即可
     *
     * @param itemList  物品列表
     * @param maxVolume 最大容量
     * @return 最大价值
     */
    public static int execute(List<Item> itemList, int maxVolume) {
        int[] dp = new int[maxVolume + 1];
        for (Item item : itemList) {
            //for (int i = 0; i < maxVolume; i++) {
            //    dp[i] = i >= item.getVolume() ? Math.max(dp[i], dp[i - item.getVolume()] + item.getValue()) : dp[i];
            //}
            // 这里直接从item.getVolume()开始，因为小于当前物品体积时，选不到，因此最优解就是取原来的值
            for (int i = item.getVolume(); i <= maxVolume; i++) {
                dp[i] = Math.max(dp[i], dp[i - item.getVolume()] + item.getValue());
            }
        }
        return dp[maxVolume];
    }
}
