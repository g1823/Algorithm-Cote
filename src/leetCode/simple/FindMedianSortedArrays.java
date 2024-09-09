package leetCode.simple;

/**
 * @author: gj
 * @date: 2024/5/30 14:04
 * @description: 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 */
public class FindMedianSortedArrays {
    public static void main(String[] args) {
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        int[] nums1 = new int[]{};
        int[] nums2 = new int[]{1};
        System.out.println(findMedianSortedArrays.execute(nums1, nums2));
    }

    public double execute(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length, leftIndex = 0, rightIndex = 0, thisIndex = 0;
        boolean isTwoFlag = totalLength % 2 == 0;
        int resultIndex = totalLength / 2;
        double result = 0;
        while (thisIndex < totalLength && leftIndex < nums1.length && rightIndex < nums2.length) {
            if (nums1[leftIndex] < nums2[rightIndex]) {
                if (isTwoFlag && thisIndex == resultIndex - 1) {
                    result = nums1[leftIndex];
                }
                if (thisIndex == resultIndex) {
                    result += nums1[leftIndex];
                }
                leftIndex++;
            } else {
                if (isTwoFlag && thisIndex == resultIndex - 1) {
                    result = nums2[rightIndex];
                }
                if (thisIndex == resultIndex) {
                    result += nums2[rightIndex];
                }
                rightIndex++;
            }
            thisIndex++;
        }
        if (thisIndex <= resultIndex && leftIndex < nums1.length) {
            while (true) {
                if (isTwoFlag && thisIndex == resultIndex - 1) {
                    result = nums1[leftIndex];
                }
                if (thisIndex == resultIndex) {
                    result += nums1[leftIndex];
                    break;
                }
                leftIndex++;
                thisIndex++;
            }
        }
        if (thisIndex <= resultIndex && rightIndex < nums2.length) {
            while (true) {
                if (isTwoFlag && thisIndex == resultIndex - 1) {
                    result = nums2[rightIndex];
                }
                if (thisIndex == resultIndex) {
                    result += nums2[rightIndex];
                    break;
                }
                rightIndex++;
                thisIndex++;
            }
        }
        if (isTwoFlag) {
            result = result / 2;
        }
        return result;
    }

}
