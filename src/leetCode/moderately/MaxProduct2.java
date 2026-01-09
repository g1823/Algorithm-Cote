package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 1339. 分裂二叉树的最大乘积
 */
public class MaxProduct2 {
    static final int MOD = 1_000_000_007;
    long maxProduct = 0;
    long total = 0;

    /**
     * 根据均值不等式：a+b >= 2ab，且 a和b的值越接近，则乘积越大
     * 因此，采用深度优先遍历，记录每个节点作为根节点的子树大小，同时计算所有节点之和，然后得到最接近(a+b)/2的节点值，并返回乘积
     * 然而不需要费力的去找最接近的节点值，先算出总和，然后直接再次遍历，遍历过程中计算 (总和 - 当前节点树之和) * 当前节点树之和，更新最大乘积
     */
    public int maxProduct(TreeNode root) {
        // 先求总和
        total = dfs(root);
        // 再计算每个子树并更新最大值
        dfs2(root);
        return (int) (maxProduct % MOD);
    }

    // 第一次DFS：求总和
    private long dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return dfs(node.left) + dfs(node.right) + node.val;
    }

    // 第二次DFS：计算每个子树和并更新最大乘积
    private long dfs2(TreeNode node) {
        if (node == null) {
            return 0;
        }
        long sum = dfs2(node.left) + dfs2(node.right) + node.val;
        maxProduct = Math.max(maxProduct, sum * (total - sum));
        return sum;
    }
}
