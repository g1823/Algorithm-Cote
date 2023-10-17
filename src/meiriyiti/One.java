package meiriyiti;

/**
 * @Author:gj
 * @DateTime:2023/10/17 20:34
 * @description: 给你一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。
 * 你必须设计并实现线性时间复杂度的算法且仅使用常量额外空间来解决此问题。
 **/
public class One {
    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 3, 2, 5};
        new One().singleNumber2(nums);
    }

    /**
     * 利用^运算符实现
     * @param nums
     * @return
     */
    public int[] singleNumber(int[] nums) {
        if (nums.length <= 2) return nums;
        int ab = 0;
        for (int num : nums) {
            ab = ab ^ num;
        }
        int pos = 0, temp = ab;
        while (temp != 0) {
            if ((temp & 1) == 1) {
                break;
            }
            pos++;
            temp = temp >> 1;
        }
        int a = 0;
        for (int num : nums) {
            if ((num >> pos & 1) == 1) a = a ^ num;
        }
        int b = ab ^ a;
        return new int[]{a, b};
    }
    //改进，改进点为求第一个不为0的位数以及求a使用的方法
    public int[] singleNumber2(int[] nums) {
        if (nums.length <= 2) return nums;
        int ab = 0;
        for (int num : nums) {
            ab = ab ^ num;
        }
        int pos = ab & (-ab);
        int a = 0;
        for (int num : nums) {
            if ((num & pos ) != 0) a = a ^ num;
        }
        int b = ab ^ a;
        return new int[]{a, b};
    }
}
