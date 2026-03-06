package leetCode.simple;

/**
 * @author: gj
 * @description: 88. 合并两个有序数组
 */
public class Merge {
    /**
     * 类似归并排序，但是从小到大排，会导致Nums1的数被覆盖
     * 因此直接从大到小排，这样一定不会被覆盖
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
        while (i >= 0) {
            nums1[k--] = nums1[i--];
        }
    }
}
