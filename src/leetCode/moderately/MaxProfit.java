package leetCode.moderately;

/**
 * @author: gj
 * @description: 309. 买卖股票的最佳时机含冷冻期
 */
public class MaxProfit {

    public static void main(String[] args) {
        int[] prices = new int[]{1, 2, 4};
        System.out.println(new MaxProfit().maxProfit(prices));
    }



    /**
     * 超时！！！
     * 深度优先，并对非正收益的情况进行剪枝。
     * 对于任意一个节点，其实只有三种情况：
     * ①未购入股票，可以选择购入或不购入当天股票
     * ②已经购入股票，可以选择在当天卖出或不卖出，此处进行剪枝，只有在当天股票价格大于买入价格时才会卖出。
     * ③昨天已经卖出股票，今天处于冷冻期，直接进入下一天。
     * 采用深度优先遍历所有情况即可
     * @param prices 股票价格
     * @return 最大收益
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 1) return result;
        dfs(0, prices, -1, 0);
        return result;
    }
    public int result = 0;
    public void dfs(int index, int[] prices, int price, int currentProfit) {
        if (index == prices.length - 1) {
            currentProfit += price == -1 || price == -2 ? 0 : Math.max(prices[prices.length - 1] - price, 0);
            result = Math.max(currentProfit, result);
            return;
        }
        if (price == -1) {
            dfs(index + 1, prices, prices[index], currentProfit);
            dfs(index + 1, prices, -1, currentProfit);
        } else if (price == -2) {
            dfs(index + 1, prices, -1, currentProfit);
        } else {
            if (prices[index] - price > 0) {
                currentProfit += prices[index] - price;
                dfs(index + 1, prices, -2, currentProfit);
                currentProfit -= prices[index] - price;
                dfs(index + 1, prices, price, currentProfit);
            } else {
                dfs(index + 1, prices, price, currentProfit);
            }
        }
    }





}
