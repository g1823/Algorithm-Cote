package leetCode.simple;

/**
 * @description: 2540. 最小公共值
 */
public class GetCommon {

    public int getCommon(int[] nums1, int[] nums2) {
        // 初始两个集合都从0开始，因为非递减，0为最小的元素
        int i =0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            // nums1当前元素小，则取Nums1的下一个元素，尽可能接近nums2的目前最小元素
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                return nums1[i];
            }
        }
        return -1;
    }
}
