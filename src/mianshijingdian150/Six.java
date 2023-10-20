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
        int[] nums = new int[]{-1, -100, 3, 99};
        new Six().rotate3(nums, 2);
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    /**
     * 额外数组：
     * 使用额外的数组，直接将每个元素放到其对应所处的位置
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
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
     * 而且可以确定，每个元素实际上都是向后移动k位置，在count到达数组长度前，绝对不会出现某个位置重复移动的问题
     *
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        //count记录跳转过的次数，当跳转过的数等于n后结束，p代表当前坐标,temp表示当前被覆盖的值
        int count = 0, p = 0, temp = nums[0];
        for (int i = 0; i < k && count < n; ) {
            p = i;
            temp = nums[p];
            while (count < n) {
                int p2 = (p + k) % n;
                int temp2 = nums[p2];
                nums[p2] = temp;
                temp = temp2;
                p = p2;
                count++;
                if (p2 == i) break;
            }
            i++;
        }
    }

    /**
     * 数组反转
     *      解决问题是可以直接对比输入输出结果，跳出中间处理，直接操作
     * 观察可知：
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 实际上，就是将数组整体反转后在分别反转前k个元素和后n-k个元素，即：
     * 1，2，3，4，5，6，7 原始
     * 7，6，5，4，3，2，1 整体反转
     * 5，6，7，4，3，2，1 反转前k个
     * 5，6，7，1，2，3，4 反转后n-k个
     *
     * @param nums
     * @param k
     */
    public void rotate3(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    //反转数组left到right
    public void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left++] = nums[right];
            nums[right--] = temp;
        }
    }

}
