package mianshijingdian150;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2023/10/18 10:16
 * @description: 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 * 示例:
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 */
public class Six {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        new Six().rotate(nums, 3);
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    /**
     * 额外数组：
     * 使用额外的数组，直接将每个元素放到其对应所处的位置
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int n = k % nums.length;
        int[] temp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            temp[i] = i < n ? nums[nums.length - n + i] : nums[i - n];
        }
        System.arraycopy(temp, 0, nums, 0, nums.length);
    }

    /**
     * 环状替换：
     * 为了减少空间复杂度，
     * 使用额外数组的原因在于如果我们直接将每个数字放至它最后的位置，这样原位置的元素会被覆盖从而丢失。
     * 因此，从另一个角度，我们可以将被替换的元素保存在变量 temp中，从而避免了额外数组的开销。
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {

    }
}
