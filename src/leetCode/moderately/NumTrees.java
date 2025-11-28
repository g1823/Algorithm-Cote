package leetCode.moderately;

/**
 * @author: gj
 * @description: 96. 不同的二叉搜索树
 */
public class NumTrees {

    public static void main(String[] args) {
        int n = 3;
        System.out.println(new NumTrees().numTrees(n));
    }

    /**
     * 思路：
     * 可以计算以i为顶点的二叉搜索树，从i遍历到n，所有情况之和则为n的不同二叉树数量
     * 计算以i为顶点的二叉搜索树数量时，比i大的数有n-i个，比i小的数有i-1个:
     * 所以以i为顶点的二叉搜索树数量实际上就等于 左侧i-1个节点能形成的二叉搜索树数量 乘以 右侧n-i个节点能形成的二叉搜索树数量
     * 可以知道，无论数组num[i]的每个元素如何，其能形成的二叉搜索树数量是固定的，也就是说，一组数据可以形成的二叉搜索树数量与这些数具体值无关，只和这组数的数量有关。
     * dp[i]的含义为i个数量的数组，可以形成的二叉搜索树数量。
     * 那么，i个数量的数组可以形成的二叉树数量 = 以第1个元素，第二个元素、、、第i个元素为顶点可形成的二叉搜索树数量之和
     * 即：dp[i] = dp[1] * dp[i-1] + dp[2] * dp[i-2] + ... + dp[i-1] * dp[0]
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        // 初始化，只有一个元素时，只有一种情况
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    /**
     * 卡特兰数
     * TODO: https://zhuanlan.zhihu.com/p/97619085
     */
    public int numTrees2(int n) {
        return 0;
    }
}
