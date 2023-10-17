package mianshijingdian150;

import java.util.Arrays;

/**
 * @Author:gj
 * @DateTime:2023/10/15 21:00
 * @description: 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 **/
public class Two {
    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        Two two = new Two();
        System.out.println(two.removeElement2(nums, 2));
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) return 0;
        // t存储等于val的值的数量
        int t = 0, i = 0;
        while (i < nums.length) {
            if (nums[i] == val) {
                i++;
                t++;
            } else {
                nums[i - t] = nums[i++];
            }
        }
        return nums.length - t;
    }

    public int removeElement2(int[] nums, int val) {
        if (nums.length == 0) return 0;
        // left存储等于val的值的数量
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[left++] = nums[i];
            }
        }
        return left;
    }
}
