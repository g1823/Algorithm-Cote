package mianshijingdian150;


import java.util.Arrays;

/**
 * @Author:gj
 * @DateTime:2023/10/14 22:09
 * @description: 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 **/
public class One {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3, n = 3;
        int[] nums2 = {2, 5, 6};
        One one = new One();
        one.merge2(nums1, m, nums2, n);
        Arrays.stream(nums1).forEach(num -> System.out.print(num + " "));
    }

    /**
     * 双指针正向比较，需要一个额外的临时数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m + n];
        if (m == 0) {
            System.arraycopy(nums2, 0, nums1, 0, n);
        } else if (n == 0) {

        } else {
            // i和j分别记录当前比较到nums1和nums2的哪个位置
            int i = 0, j = 0, t = 0;
            while (i < m && j < n) {
                if (nums1[i] <= nums2[j]) temp[t++] = nums1[i++];
                else temp[t++] = nums2[j++];
            }
            while (i < m) temp[t++] = nums1[i++];
            while (j < n) temp[t++] = nums2[j++];
            System.arraycopy(temp, 0, nums1, 0, m + n);
        }
    }

    /**
     * 双指针逆向比较，从数组尾部比较，减少空间复杂度
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        if (m == 0) System.arraycopy(nums2, 0, nums1, 0, n);
        if (n == 0) return;
        int p1 = m - 1, p2 = n - 1, p3 = m + n - 1;
        while (p1 >= 0 && p2 >= 0) nums1[p3--] = nums1[p1] > nums2[p2] ? nums1[p1--] : nums2[p2--];
        while (p2 >= 0) nums1[p3--] = nums2[p2--];
    }
}
