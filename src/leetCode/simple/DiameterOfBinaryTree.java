package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 543. 二叉树的直径
 */
public class DiameterOfBinaryTree {
    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        DiameterOfBinaryTree diameterOfBinaryTree = new DiameterOfBinaryTree();
        System.out.println(diameterOfBinaryTree.diameterOfBinaryTree(treeNode1));
    }

    int maxNum = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return maxNum;
    }

    /**
     * 回溯法
     * @param node 根节点
     * @return 当前节点深度
     */
    public int dfs(TreeNode node) {
        int leftDepth = 0, rightDepth = 0;
        if (node.right == null && node.left == null) return 1;
        if (node.left != null) leftDepth = dfs(node.left);
        if (node.right != null) rightDepth = dfs(node.right);
        maxNum = Math.max(leftDepth + rightDepth, maxNum);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
