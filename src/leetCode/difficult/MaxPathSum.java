package leetCode.difficult;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 124. 二叉树中的最大路径和
 */
public class MaxPathSum {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(8);
        TreeNode node3 = new TreeNode(11);
        TreeNode node4 = new TreeNode(13);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(7);
        TreeNode node7 = new TreeNode(2);
        TreeNode node8 = new TreeNode(1);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node5.right = node8;
        int i = new MaxPathSum().maxPathSum(root);
        System.out.println(i);
    }

    int allMax = Integer.MIN_VALUE;

    /**
     * 解题思路：
     * 定义“路径”含义：
     * - 路径不一定从根节点开始或到叶子节点结束，但路径中不能重复节点；
     * - 可以经过某个节点，向左和右各走一段（但不能再往上传）。
     * 对于任意节点，考虑两种情况：
     * - 当前节点为路径中的“中间节点”：路径是 left + node.val + right，这表示路径在此节点分叉；
     * - 当前节点向上传递路径时：只能选择 max(left, right) + node.val，因为不能分叉，只能往父节点延伸。
     * 用后序遍历（自底向上），递归地计算每个节点：
     * - 向上传递“最大单侧路径和”；
     * - 同时记录当前节点为根时的“完整路径和”，更新全局最大值。
     * 剪枝优化：
     * - 如果左子树或右子树返回的路径和为负数，就直接舍弃（视为 0），避免拖累整体路径。
     */
    public int maxPathSum(TreeNode root) {
        backtrack(root);
        return allMax;
    }

    public int backtrack(TreeNode node) {
        if (node == null) {
            return 0;
        }
        /**
         * 小于0就舍弃,可以参考最大子序列和问题求解{@link basicAlgorithm.greedy.MaximumSumSubarray.solution4}
         */
        int left = Math.max(0, backtrack(node.left));
        int right = Math.max(0, backtrack(node.right));

        // 当前节点为根的路径和
        int curPathSum = left + right + node.val;
        // 更新全局最大路径和
        allMax = Math.max(allMax, curPathSum);

        // 返回最大单侧路径和，供父节点使用（只能走一边）
        return Math.max(left, right) + node.val;
    }
}
