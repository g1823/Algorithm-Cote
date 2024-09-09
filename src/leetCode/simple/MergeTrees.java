package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @Package leetCode
 * @Date 2024/9/8 11:59
 * @Author gaojie
 * @description: 617. 合并二叉树
 */
public class MergeTrees {
    public static void main(String[] args) {
        MergeTrees mergeTrees = new MergeTrees();
        TreeNode root1 = new TreeNode(3);
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(5);
        TreeNode node3 = new TreeNode(1);
        TreeNode node4 = new TreeNode(2);
        root1.left = node1;
        root1.right = node2;
        node1.left = node3;
        node1.right = node4;
        TreeNode root2 = new TreeNode(4);
        TreeNode node5 = new TreeNode(1);
        TreeNode node6 = new TreeNode(2);
        TreeNode node7 = new TreeNode(1);
        root2.left = node5;
        root2.right = node6;
        node5.left = node7;
        mergeTrees.mergeTrees(root1, root2);
    }

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;
        dfs(root1, root2);
        return root1;
    }

    public void dfs(TreeNode node1, TreeNode node2) {
        node1.val += node2.val;
        boolean left = true, right = true;
        if (node1.left == null) {
            if (node2.left != null) node1.left = node2.left;
            left = false;
        }
        if (node1.right == null) {
            if (node2.right != null) node1.right = node2.right;
            right = false;
        }
        if (node2.left == null) left = false;
        if (node2.right == null) right = false;
        if (left) dfs(node1.left, node2.left);
        if (right) dfs(node1.right, node2.right);
    }
}
