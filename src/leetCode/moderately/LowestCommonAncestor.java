package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 236. 二叉树的最近公共祖先
 */
public class LowestCommonAncestor {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(1);
        TreeNode node3 = new TreeNode(6);
        TreeNode node4 = new TreeNode(2);
        TreeNode node5 = new TreeNode(0);
        TreeNode node6 = new TreeNode(8);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(4);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node4.left = node7;
        node4.right = node8;
        TreeNode result = new LowestCommonAncestor().lowestCommonAncestor(root, node1, node8);
        System.out.println(result);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> result = new ArrayList<>();
        dfs(root, p, q, result);
        return result.get(0);
    }

    public boolean dfs(TreeNode node, TreeNode p, TreeNode q, List<TreeNode> result) {
        if (node == p || node == q) return true;
        if (node == null) return false;
        if (!result.isEmpty()) return true;
        boolean left = dfs(node.left, p, q, result);
        boolean right = dfs(node.right, p, q, result);
        if (left && right) {
            result.add(node);
            return true;
        }
        return false;
    }
}
