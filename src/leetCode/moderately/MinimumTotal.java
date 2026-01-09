package leetCode.moderately;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: gj
 * @description: 120. 三角形最小路径和
 */
public class MinimumTotal {
    public static void main(String[] args) {
        List<List<Integer>> triangle = new ArrayList<>();
        Collections.addAll(triangle,
                new ArrayList<>(Arrays.asList(2)),
                new ArrayList<>(Arrays.asList(3, 4)),
                new ArrayList<>(Arrays.asList(6, 5, 7)),
                new ArrayList<>(Arrays.asList(4, 1, 8, 3)));
        System.out.println(new MinimumTotal().minimumTotal(triangle));
    }

    /**
     * 思路：
     * 这道题比较简单，很容易得出状态转移方程，可以发现：
     * 对于任意节点，假设当前节点为 (i, j)，只能从两个节点到达当前节点： (i-1, j) 和 (i-1, j-1)
     * 也就是说，只需要直到到达 (i-1, j) 和 (i-1, j-1)的最小值，就可以求出到达 dp[i][j] 的值了。
     * 状态转移方程：
     * dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-1]) + triangle[i][j]
     * 注意：
     * 可以直接进行空间优化，采用一维滚动数组即可，不过当前行右侧数据依赖于左侧数据，所以需要从右往左遍历。
     * 当前行的元素个数比上一行大1，所以计算数组越界时，判断逻辑为j >= row.size() - 1
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size()];
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < triangle.size(); i++) {
            List<Integer> row = triangle.get(i);
            for (int j = row.size() - 1; j >= 0; j--) {
                int left = j - 1 < 0 ? Integer.MAX_VALUE : dp[j - 1];
                // 上一行比当前行少一位，所以这里判断为 j >= row.size() - 1。
                int top = j >= row.size() - 1 ? Integer.MAX_VALUE : dp[j];
                dp[j] = Math.min(left, top) + row.get(j);
            }
        }
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < triangle.size(); i++) {
            minSum = Math.min(minSum, dp[i]);
        }
        return minSum;
    }
}
