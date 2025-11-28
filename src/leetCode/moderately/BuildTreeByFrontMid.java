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
     * 递归
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length > 0) {
            return recursion(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
        }
        return null;
    }

    public TreeNode recursion(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        if (preStart == preEnd && inStart == inEnd) {
            return new TreeNode(preorder[preStart]);
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        if (preStart < preEnd && inStart < inEnd) {
            int rootVal = preorder[preStart];
            TreeNode left = null, right = null;
            for (int i = inStart; i <= inEnd; i++) {
                if (inorder[i] == rootVal) {
                    int leftSize = i - inStart;
                    left = recursion(preorder, inorder, preStart + 1, preStart + leftSize, inStart, i - 1);
                    right = recursion(preorder, inorder, preStart + leftSize + 1, preEnd, i + 1, inEnd);
                }
            }
            root.left = left;
            root.right = right;
        }
        return root;
    }

    /**
     * TODO：迭代
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        return null;
    }
}
