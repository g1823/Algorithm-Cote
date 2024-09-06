package leetCode;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 543. 二叉树的直径
 */
public class DiameterOfBinaryTree {
    int maxNum = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root, 0);
        return maxNum;
    }

    public int dfs(TreeNode node, int maxNum) {
        int leftDepth = 0, rightDepth = 0;
        if (node.right == null && node.left == null) return 0;
        if (node.left != null) leftDepth = dfs(node.left, maxNum);
        if (node.right != null) rightDepth = dfs(node.right, maxNum);
        maxNum = Math.max(leftDepth + rightDepth, maxNum);
        return Math.max(leftDepth, rightDepth);
    }
}
