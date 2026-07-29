package leetCode.difficult;

import java.util.*;

/**
 * @description: 493. 翻转对
 */
public class ReversePairs {
    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 3, 1};
        System.out.println(new ReversePairs().reversePairs(nums));
    }

    /**
     * 树状数组BIT
     * 题目要求很简单，其实就是统计下标比自己大的范围内符合某种条件的元素个数。
     * 直接离散化，将所有可能的数都枚举出来做映射，然后排序使其单调，就可以直接使用树状数组了
     */
    public int reversePairs(int[] nums) {
        Set<Long> set = new HashSet<>();
        for (int num : nums) {
            set.add((long) num);
            set.add((long) num * 2);
        }
        long[] arr = new long[set.size()];
        int index = 0;
        for (Long num : set) {
            arr[index++] = num;
        }
        Arrays.sort(arr);
        Map<Long, Integer> map = new HashMap<>();
        // 树状数组从1开始
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i + 1);
        }
        int res = 0;
        int[] bit = new int[arr.length + 1];
        // 倒序添加
        for (int i = nums.length - 1; i >= 0; i--) {
            int num = nums[i];
            // 查询严格小于 num 的 2*nums[j] 的个数
            res += query(bit, map.get((long) num) - 1);
            // 将当前元素的 2*num 加入树状数组
            update(bit, map.get((long) num * 2));
        }
        return res;
    }

    private int lowbit(int x) {
        return x & -x;
    }

    private int query(int[] bit, int index) {
        int res = 0;
        while (index > 0) {
            res += bit[index];
            index -= lowbit(index);
        }
        return res;
    }

    private void update(int[] bit, int index) {
        while (index < bit.length) {
            bit[index]++;
            index += lowbit(index);
        }
    }
}
