package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 112. 路径总和
 */
public class HasPathSum {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        node1.left = node2;
        System.out.println(new HasPathSum().hasPathSum(node1, 0));
    }

    /**
     * 注意审题：是从根节点到叶子结点的和，因此条件需改为左右子节点均为空才进行对比
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return dfs(root, targetSum, 0);
    }

    public boolean dfs(TreeNode node, int targetSum, int currSum) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) {
            return currSum + node.val == targetSum;
        }
        currSum += node.val;
        return dfs(node.left, targetSum, currSum) || dfs(node.right, targetSum, currSum);
    }
}
