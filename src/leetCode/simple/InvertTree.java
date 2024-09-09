package leetCode.simple;

import leetCode.help.TreeNode;

import java.util.LinkedList;

/**
 * @author: gj
 * @description: 226 反转二叉树
 */
public class InvertTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        InvertTree invertTree = new InvertTree();
        invertTree.invertTree(root);
    }

    public TreeNode invertTree(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode leftNode = node.left;
            TreeNode rightNode = node.right;
            node.left = rightNode;
            node.right = leftNode;
            if (leftNode != null) {
                queue.add(leftNode);
            }
            if (rightNode != null) {
                queue.add(rightNode);
            }
        }
        return root;
    }
}
