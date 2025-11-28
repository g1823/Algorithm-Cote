package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 106. 从中序与后序遍历序列构造二叉树
 */
public class BuildTreeByMidBackend {

    public static void main(String[] args) {
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        int[] postorder = new int[]{9, 15, 7, 20, 3};
        System.out.println(new BuildTreeByMidBackend().buildTree(inorder, postorder));
    }

    /**
     * 根据中序遍历（inorder）和后序遍历（postorder）构造二叉树。
     * 【思路分析】
     * --------------------------------------------------------
     * 1️⃣ 后序遍历的最后一个节点一定是当前子树的根节点。
     * 2️⃣ 在中序遍历中找到这个根节点，可以划分出左、右子树的区间：
     * - 中序左半部分为左子树节点；
     * - 中序右半部分为右子树节点。
     * 3️⃣ 知道左子树的节点数量后，就能在后序遍历中确定左右子树对应的区间范围：
     * - 左子树在后序中的区间：[postStart, postStart + leftSize - 1]
     * - 右子树在后序中的区间：[postStart + leftSize, postEnd - 1]
     * 4️⃣ 按照这种方式递归构造左右子树。
     * 【算法关键点】
     * - 通过“节点数量”来拆分后序遍历区间，而不是通过具体节点值。
     * - 中序遍历提供了根节点在整个序列中的位置，是划分左右子树的关键。
     * - 每个节点值必须唯一，否则无法通过值在中序中定位根节点。
     * 时间复杂度：O(n²)（因为每次都线性扫描 inorder），可优化为 O(n)，通过哈希表记录 inorder 中每个值对应的索引。
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (postorder.length == 0) {
            return null;
        }
        if (postorder.length == 1) {
            return new TreeNode(postorder[0]);
        }
        return recursion(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
    }

    /**
     * 递归构建二叉树
     */
    public TreeNode recursion(int[] inorder, int[] postorder, int inStart, int inEnd, int postStart, int postEnd) {
        // 1️⃣ 边界条件：区间越界表示无子树
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }
        // 2️⃣ 后序区间最后一个元素为当前子树根节点
        TreeNode root = new TreeNode(postorder[postEnd]);
        // 3️⃣ 在线性扫描中序遍历，找到根节点位置，以划分左右子树
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == postorder[postEnd]) {
                // 左子树节点数量
                int leftSize = i - inStart;
                // 4️⃣ 递归构建左子树
                root.left = recursion(inorder, postorder, inStart, i - 1, postStart, postStart + leftSize - 1);
                // 5️⃣ 递归构建右子树
                root.right = recursion(inorder, postorder, i + 1, inEnd, postStart + leftSize, postEnd);
                break;
            }
        }
        return root;
    }

}
