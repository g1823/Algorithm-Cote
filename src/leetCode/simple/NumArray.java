package leetCode.simple;

/**
 * @author: gj
 * @description: 303. 区域和检索 - 数组不可变
 */
public class NumArray {
    private int[] sums;

    // 前缀和
    public NumArray(int[] nums) {
        sums = new int[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sums[i] = sums[i - 1] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return left == 0 ? sums[right] : sums[right] - sums[left - 1];
    }
}
