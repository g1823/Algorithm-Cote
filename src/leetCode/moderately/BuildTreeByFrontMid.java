package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 105. 从前序与中序遍历序列构造二叉树
 */
public class BuildTreeByFrontMid {
    public static void main(String[] args) {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        System.out.println(new BuildTreeByFrontMid().buildTree(preorder, inorder));
    }


    /**
     * 递归：（关键点在于通过中序遍历得到左右子树的数量，而非反推下一个根节点之类）
     * 前序遍历：根节点 -> 左子树 -> 右子树
     * 中序遍历：左子树 -> 根节点 -> 右子树
     * 题目说明了每个节点值都不相同，那么可以通过前序遍历的第一个节点确定根节点，然后找到中序遍历中的该节点
     * 再根据中序遍历性质，可以得到根节点左侧的节点为左子树，右侧节点为右子树，然后得到左右子树的节点数量
     * 再根据前序遍历的性质，根节点后是连续的左子树，左子树结束后才是右子树，加上中序遍历得到的左右子树节点数，那么就能确定前序遍历中左右子树的节点范围
     * 然后再一次递归着处理左右子树即可。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        return recursion(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode recursion(int[] preorder, int[] inorder,
                              int preStart, int preEnd,
                              int inStart, int inEnd) {
        // 区间越界，表示当前子树为空
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        // 前序遍历区间第一个节点为当前子树根节点
        TreeNode root = new TreeNode(preorder[preStart]);
        int rootVal = preorder[preStart];

        // 在中序遍历中找到根节点位置，用于划分左右子树
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                // 左子树节点数量
                int leftSize = i - inStart;
                // 构造左子树
                root.left = recursion(preorder, inorder, preStart + 1, preStart + leftSize, inStart, i - 1);
                // 构造右子树
                root.right = recursion(preorder, inorder, preStart + leftSize + 1, preEnd, i + 1, inEnd);
                break;
            }
        }
        return root;
    }

    /**
     * TODO：迭代
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        TreeNode dummy = new TreeNode(0);
        TreeNode cur = dummy;

        return null;
    }
}
