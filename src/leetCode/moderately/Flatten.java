package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 114. 二叉树展开为链表
 */
public class Flatten {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        new Flatten().flatten2(root);
        System.out.println();
    }

    public void flatten(TreeNode root) {
        recursion(root);
    }

    /**
     * 递归，记录当前节点的右节点，递归返回最后一个节点，然后左节点遍历结束后的最后一个节点再连上右节点
     * 由于递归，空间复杂度会达到树的深度
     * @param root TreeNode
     * @return 当前遍历的最后一个节点
     */
    public TreeNode recursion(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            return root;
        }
        TreeNode rightNode = root.right;
        TreeNode lastNode = null;
        if (root.left != null) {
            root.right = root.left;
            root.left = null;
            lastNode = recursion(root.right);
        }else{
            lastNode = root;
        }
        if (rightNode != null) {
            lastNode.right = rightNode;
            lastNode = recursion(rightNode);
        }
        return lastNode;
    }


    /**
     * 转换：从上到下，将每个节点的右子节点转换为前缀切点的右子节点
     * 前缀节点指的是左子节点的最后一个元素
     * @param root 根节点
     */
    public void flatten2(TreeNode root) {
        if (root == null){
            return;
        }
        TreeNode node = root;
        while (node != null) {
            if (node.left != null){
                if(node.right != null){
                    TreeNode preNode = getPreNode(node.left);
                    preNode.right = node.right;
                }
                node.right = node.left;
                node.left = null;
            }
            node = node.right;
        }
    }

    private TreeNode getPreNode(TreeNode node) {
        TreeNode result = node;
        while (result.right != null){
            result = result.right;
        }
        return result;
    }
}
