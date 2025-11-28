package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 108. 将有序数组转换为二叉搜索树
 */
public class SortedArrayToBST {

    /**
     * 思路：
     * 因为数据是有序的，那么中位数就是根节点，左右两边的数组就是左右子树，递归处理即可
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        int mid = nums.length / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = dfs(nums, 0, mid - 1);
        root.right = dfs(nums, mid + 1, nums.length - 1);
        return root;
    }

    public TreeNode dfs(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        if (left == right) {
            return new TreeNode(nums[left]);
        }
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.right = dfs(nums, mid + 1, right);
        root.left = dfs(nums, left, mid - 1);
        return root;
    }
}
