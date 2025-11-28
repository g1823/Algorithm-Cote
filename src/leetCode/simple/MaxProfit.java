package leetCode.simple;

/**
 * @author: gj
 * @description: 121. 买卖股票的最佳时机
 */
public class MaxProfit {
    /**
     * 题意为只允许买卖一次，求最大利润
     * 那么只需要直到对于任意一天，在其之前的所有天中股票价格最低的那天，就是当天卖出的最低值
     */
    public int maxProfit(int[] prices) {
        int result = 0, temMax = 0;
        for (int i = prices.length - 2; i >= 0; i--) {
            temMax = Math.max(temMax + prices[i + 1] - prices[i], prices[i + 1] - prices[i]);
            result = Math.max(temMax, result);
        }
        return result;
    }
}
