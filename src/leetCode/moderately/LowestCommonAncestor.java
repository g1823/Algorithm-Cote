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
        if (node == null) return false;
        if (!result.isEmpty()) return true;
        boolean left = dfs(node.left, p, q, result);
        boolean right = dfs(node.right, p, q, result);
        // 当还未找到公共父节点 && [左子树和右子树分别含有一个待寻找节点 或 当前节点等于待寻找节点且左子树或右子树已经找到另一个节点]
        if (result.isEmpty() && (left && right) || (node == p || node == q) && (left || right)) {
            result.add(node);
        }
        // 只要当前节点子树含有带寻找节点或当前节点等于待寻找节点则返回true；
        // 注意，这一步判断需要放在最后，避免先找到节点P,然后节点q在节点p的子树中直接返回找不到。
        return left || right || (node == p || node == q);
    }
}
