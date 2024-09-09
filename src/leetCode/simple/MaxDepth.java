package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @Package leetCode
 * @Date 2024/9/8 16:02
 * @Author gaojie
 * @description: 104. 二叉树的最大深度
 */
public class MaxDepth {
    int maxDepth = 0;

    public int maxDepth(TreeNode root) {
        dfs(root);
        return maxDepth;
    }

    public int dfs(TreeNode node) {
        if (node == null) return 0;
        int leftDepth = dfs(node.left) + 1;
        int rightDepth = dfs(node.right) + 1;
        int currentMaxDepth = Math.max(leftDepth, rightDepth);
        maxDepth = Math.max(currentMaxDepth, maxDepth);
        return currentMaxDepth;
    }

}
