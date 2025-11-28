package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 230. 二叉搜索树中第 K 小的元素
 */
public class KthSmallest {

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(2);
        node.left = node1;
        node.right = node2;
        node1.right = node3;
        System.out.println(new KthSmallest().kthSmallest(node, 1));
    }

    /**
     * 核心思路：
     * 1. BST 的中序遍历结果是递增有序的，因此第 k 小的元素就是中序遍历的第 k 个节点。
     * 2. 在递归过程中，dfs 方法会返回当前子树的节点总数，并在遍历过程中判断是否到达第 k 个节点。
     * 3. 具体做法：
     * - 递归遍历左子树，得到左子树的节点数 leftCount。
     * - 若 leftCount + 1 == k，说明当前节点正好是第 k 小的元素，记录并标记 found 为 true。
     * - 否则，根据左子树节点数调整 k（k - leftCount - 1）后递归遍历右子树。
     * - 每次递归都返回当前子树节点数，用于上层判断。
     * 4. 为了避免不必要的遍历，一旦 found 为 true，就立即终止后续递归。
     */

    public int kthSmallest(TreeNode root, int k) {
        Result result = new Result();
        dfs(root, k, result);
        return result.val;
    }

    private int dfs(TreeNode node, int k, Result result) {
        // 空子树节点数为 0
        if (node == null) {
            return 0;
        }
        // 遍历左子树
        int leftCount = dfs(node.left, k, result);
        // 已找到，提前终止
        if (result.found) {
            return 0;
        }
        // 当前节点
        if (leftCount + 1 == k) {
            result.val = node.val;
            result.found = true;
            return 0;
        }

        // 遍历右子树
        int rightCount = dfs(node.right, k - leftCount - 1, result);
        if (result.found) {
            return 0;
        }
        // 返回当前子树的节点数
        return leftCount + rightCount + 1;
    }

    private static class Result {
        int val;
        boolean found = false;
    }
}
