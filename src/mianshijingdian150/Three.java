package mianshijingdian150;

import java.util.Arrays;

/**
 * @Author:gj
 * @DateTime:2023/10/17 20:27
 * @description: 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
 **/
public class Three {
    public static void main(String[] args) {
        Three three = new Three();
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(three.removeDuplicates(nums));
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    /**
     * 双指针
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int sum = 1, left = 1;
        for (int i = 0, temp = nums[0]; i < nums.length; i++) {
            if (nums[i] != temp) {
                nums[left++] = nums[i];
                temp = nums[i];
            }
        }
        return left;
    }
}
