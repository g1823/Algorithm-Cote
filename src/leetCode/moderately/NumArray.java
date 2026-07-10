package leetCode.moderately;

/**
 * @description: 307. 区域和检索 - 数组可修改
 */
public class NumArray {

    static class TreeNode {
        int val, l, r;
        TreeNode left, right;

        TreeNode(int x, int l, int r) {
            val = x;
            this.l = l;
            this.r = r;
            left = right = null;
        }
    }

    TreeNode root;

    public NumArray(int[] nums) {
        root = buildTree(nums, 0, nums.length - 1);
    }

    public void update(int index, int val) {
        update(root, index, val);
    }

    public int sumRange(int left, int right) {
        return sumRange(root, left, right);
    }

    private TreeNode buildTree(int[] nums, int start, int end) {
        // 叶子节点
        if (start == end) {
            return new TreeNode(nums[start], start, end);
        }
        TreeNode node = new TreeNode(0, start, end);
        int mid = (start + end) / 2;
        node.left = buildTree(nums, start, mid);
        node.right = buildTree(nums, mid + 1, end);
        node.val = node.left.val + node.right.val;
        return node;
    }

    private void update(TreeNode node, int index, int val) {
        if (node.l == node.r) {
            if (node.l == index) {
                node.val = val;
            }
            return;
        }
        int l = node.l;
        int r = node.r;
        int mid = (l + r) / 2;
        if (index <= mid) {
            update(node.left, index, val);
        } else {
            update(node.right, index, val);
        }
        node.val = node.left.val + node.right.val;
    }

    private int sumRange(TreeNode node, int start, int end) {
        // 刚好和区间一致
        if (start == node.l && end == node.r) {
            return node.val;
        }
        int mid = (node.l + node.r) / 2;
        // 完全在左边
        if (end <= mid) {
            return sumRange(node.left, start, end);
        }
        // 完全在右边
        else if (start >= mid + 1) {
            return sumRange(node.right, start, end);
        }
        // 左右各一部分
        else {
            return sumRange(node.left, start, mid) + sumRange(node.right, mid + 1, end);
        }
    }
}

