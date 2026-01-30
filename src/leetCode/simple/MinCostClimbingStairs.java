package leetCode.simple;

/**
 * @author: gj
 * @description: 746. 使用最小花费爬楼梯
 */
public class MinCostClimbingStairs {

    public int minCostClimbingStairs(int[] cost) {
        if(cost.length == 2){
            return Math.min(cost[0], cost[1]);
        }
        int x1 = cost[0], x2 = cost[1];
        for (int i = 2; i < cost.length; i++) {
            int temp = x2;
            x2 = Math.min(x1 + cost[i], x2 + cost[i]);
            x1 = temp;
        }
        return Math.min(x1, x2);
    }
}
