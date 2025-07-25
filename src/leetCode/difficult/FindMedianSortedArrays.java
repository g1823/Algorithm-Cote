package leetCode.difficult;

/**
 * @author: gj
 * @date: 2024/5/30 14:04
 * @description: 4. 寻找两个正序数组的中位数
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 */
public class FindMedianSortedArrays {
    public static void main(String[] args) {
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        int[] nums1 = new int[]{};
        int[] nums2 = new int[]{1};
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(nums1, nums2));
    }

    /**
     * 模拟归并两个有序数组的过程，直到走到中位数位置为止
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length;

        // 是否是偶数长度，决定是否取两个中位数求平均
        boolean isTwoFlag = totalLength % 2 == 0;
        int resultIndex = totalLength / 2;

        // 定义两个指针分别指向 nums1 和 nums2
        int leftIndex = 0, rightIndex = 0, thisIndex = 0;
        double result = 0;

        // 模拟归并过程，直到 thisIndex 走到 resultIndex 位置
        while (thisIndex < totalLength && leftIndex < nums1.length && rightIndex < nums2.length) {
            int current;
            if (nums1[leftIndex] < nums2[rightIndex]) {
                current = nums1[leftIndex++];
            } else {
                current = nums2[rightIndex++];
            }

            // 记录中位数前一个值（用于偶数情况）
            if (isTwoFlag && thisIndex == resultIndex - 1) {
                result = current;
            }

            // 记录中位数（若是奇数，只记录这一个；若是偶数，需要加上前一个）
            if (thisIndex == resultIndex) {
                result += current;
                break;
            }

            thisIndex++;
        }

        // 如果 nums1 还有剩余
        while (thisIndex <= resultIndex && leftIndex < nums1.length) {
            int current = nums1[leftIndex++];
            if (isTwoFlag && thisIndex == resultIndex - 1) {
                result = current;
            }
            if (thisIndex == resultIndex) {
                result += current;
                break;
            }
            thisIndex++;
        }

        // 如果 nums2 还有剩余
        while (thisIndex <= resultIndex && rightIndex < nums2.length) {
            int current = nums2[rightIndex++];
            if (isTwoFlag && thisIndex == resultIndex - 1) {
                result = current;
            }
            if (thisIndex == resultIndex) {
                result += current;
                break;
            }
            thisIndex++;
        }

        // 偶数情况，结果要除以2
        if (isTwoFlag) {
            result /= 2;
        }

        return result;
    }


    /**
     * 使用二分查找法在两个有序数组中寻找中位数，时间复杂度为 O(log(min(m, n)))
     * <p>
     * findMedianSortedArrays2 是对归并思路的优化，归并合并法需要一个一个向右移动指针，时间复杂度为 O(m+n)
     * 考虑到有序数组查找问题通常可以借助二分查找优化，因此可以将时间复杂度降为 O(log(min(m, n)))
     * <p>
     * 解法思路：
     * 1、始终选择较短的数组作为二分查找的基础数组，避免越界问题
     * 2、通过 k = (m + n + 1) / 2，统一奇偶两种情况，使得“左边始终有 k 个元素”
     * 3、假设从 nums1 中选择 i 个元素放在左边，那么 nums2 中就选择 j = k - i 个元素放在左边
     * <p>
     * 目标：划分两个数组为“左半部分”和“右半部分”，使得：
     * - 左半部分的最大值 ≤ 右半部分的最小值
     * - 此时中位数一定在这四个边界值中产生
     * <p>
     * 数学条件如下：
     * - nums1[i - 1] <= nums2[j]
     * - nums2[j - 1] <= nums1[i]
     * <p>
     * 这说明划分合法，左侧刚好有 k 个元素，右侧剩余部分在合并后处于中位数右边
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        // 保证第一个入参一定是较短的数组，这样对其进行二分查找，时间复杂度会低一些
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays2(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;
        /**
         * 奇偶统一逻辑，使得无论总长度是奇数还是偶数，都可以通过划分左右两边的长度，使“左边包含 k 个元素”，从而统一处理
         * 设总长度为 m + n：
         * - 若是 奇数：比如 5，中位数是第 3 个，即 k = 3 = (5 + 1) / 2。
         * - 若是 偶数：比如 6，我们要找第 3 和第 4 个数的平均值：
         * - - 左边包含 3 个元素（k = 3）:(6+1)/2 = 3
         * - - 右边包含 3 个元素（总共 6 个）
         * 所以，无论是奇数还是偶数，总是让左边包含 (m + n + 1) / 2 个元素。
         * 多出来的那个“1”正是为奇数时给左边多分一个位置，方便统一逻辑处理“中位数是左边最大值”，对偶数则刚好平均。
         */
        int k = (m + n + 1) / 2;
        while (left <= right) {
            int mid = (left + right) / 2;
            int j = k - mid;

            /**
             * 如果 mid == 0，说明 nums1 划分点在最左边，那：
             * - nums1LeftMax 是非法的 nums1[-1]，但我们需要它参与比较，所以赋值为 Integer.MIN_VALUE（左边没有元素，最大值无限小）
             * 如果 mid == m，说明 nums1 划分点在最右边，那：
             * - nums1RightMin 是非法的 nums1[m]，所以赋值为 Integer.MAX_VALUE（右边没有元素，最小值无限大）
             * 同理，j == 0 或 j == n 也一样处理。
             */
            // nums1的mid下标前一个元素
            int nums1LeftMax = (mid == 0) ? Integer.MIN_VALUE : nums1[mid - 1];
            // nums1的mid下标元素
            int nums1RightMin = (mid == m) ? Integer.MAX_VALUE : nums1[mid];
            // nums2的j下标前一个元素
            int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            // nums2的j下标元素
            int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                // 当nums1[mid-1]<=nums2[j]，nums2[j-1]<=nums1[mid]，
                // 说明两个数组合并后，nums1[mid]和nums2[j]会是右半部分可能为最小的两个元素，且二者前面的元素数量之和加起来是k个。
                // 即mid和j就是要找的值
                if ((m + n) % 2 == 0) {
                    // 总数为偶数个，需要取两个数中的平均数，即
                    // nums1[mid-1]和nums2[j-1]之间较大的数 对应合并后数组的第k个元素
                    // nums1[mid]和nums2[j]之间较小的数 对应合并后数组的第k+1个元素
                    // 两数之和的平均数
                    return (Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2.0;
                } else {
                    // 总数为奇数个，只需要取nums1[mid-1]和nums2[j-1]之间较大的数
                    return Math.max(nums1LeftMax, nums2LeftMax);
                }
            } else if (nums1LeftMax > nums2RightMin) {
                // nums1[mid-1]，即上一个元素比Nums2[j]还要大，左移
                right = mid - 1;
            } else {
                // nums2[j-1]，即上一个元素比Nums1[mid]还要大，右移
                left = mid + 1;
            }
        }
        return -1;
    }

}
