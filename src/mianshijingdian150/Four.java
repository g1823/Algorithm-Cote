package mianshijingdian150;

import java.util.Arrays;

/**
 * @Author:gj
 * @DateTime:2023/10/17 20:29
 * @description: 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 **/
public class Four {
    public static void main(String[] args) {
        Four four = new Four();
        int[] nums = {1, 1, 1, 1, 2, 2, 3};
        System.out.println(new Four().removeDuplicates2(nums));
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    /**
     * 双指针+计数
     * p1表示慢的指针，即当前存储到的位置
     * p2存储快的指针，即已经遍历到的位置
     * tempNum存储当前元素重复的次数，tempValue存储当前元素的值
     * 当一个数重复超过两次就说明需要被覆盖，用慢指针指向其位置
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        //p1表示慢的指针，即当前存储到的位置；p2存储快的指针，即已经遍历到的位置；tempNum存储当前元素重复的次数，tempValue存储当前元素的值
        int tempNum = 0, tempValue = nums[0], p1 = 0, p2 = 0;
        while (p1 < nums.length) {
            if (nums[p1] == tempValue) {
                if (++tempNum <= 2) {
                    nums[p2++] = nums[p1];
                }
            } else {
                nums[p2++] = nums[p1];
                tempValue = nums[p1];
                tempNum = 1;
            }
            p1++;
        }
        nums[p2 - 1] = nums[p1 - 1];
        return p2;
    }
    //改进
    public int removeDuplicates2(int[] nums) {
        //当数量小于2时直接返回即可。
        if (nums.length <= 2) return nums.length;
        //直接从第三个元素(下标2)开始比较
        int slow = 2, fast = 2;
        //对后面的每一位进行继续遍历，能够保留的前提是与当前位置的前面 k 个元素不同（答案中的第一个 1）。
        while (fast < nums.length) {
            //如果当前下标fast的值和当前记录位置slow的前2个元素相同，说明不需要保留
            //当不同时，说明需要替换
            if (nums[fast] != nums[slow - 2]) {
                nums[slow++]=nums[fast];
            }
            fast++;
        }
        return slow;
    }
}
