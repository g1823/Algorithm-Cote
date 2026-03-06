package leetCode.moderately;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author: gj
 * @description: 658. 找到 K 个最接近的元素
 */
public class FindClosestElements {

    /**
     * 思路：
     * 设 找到小于等于x的元素中下标最大的一个下标 = index
     * 因为最接近x的k个元素一定在 Math.max(0, index -k) 到 Math.min(arr.length - 1, index + k)之间, 超出这个区间的其他k个元素，一定比当前区间内的差值更大
     * 1、利用二分查找，找到小于等于x的元素中下标最大的一个下标
     * 2、初始化窗口：然后用该下标 - k + 1为窗口最左侧下标，left + k - 1为窗口最右侧下标
     * 3、记录窗口内差值绝对值之和，每次向右滑动，只需要减去最左侧元素，加上最右侧元素，即可更新窗口内的差值绝对值之和
     * 4、维护最小差值绝对值之和的左侧下标 minLeft， 最终minLeft -> minLeft + k - 1为最接近的k个元素
     * 二分查找：O(log n)
     * 滑动窗口：窗口数量 ≤ (index + k) - (index - k + 1) + 2 ≈ 2k，每个窗口更新为 O(1)，总时间 O(k)
     * 总复杂度：O(log n + k)，与标准解法一致。
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // 边界情况：如果 k 大于等于数组长度，直接返回所有元素
        if (k >= arr.length) {
            List<Integer> result = new ArrayList<>(arr.length);
            for (int num : arr) {
                result.add(num);
            }
            return result;
        }
        // 1. 找到小于等于 x 的元素中下标最大的一个下标
        int index = findLastLessOrEqual(arr, x);
        // 2. 初始化窗口左边界
        // 原逻辑：如果 index + 1 > k，则 left = index - k + 1，否则为 0
        // 优化：使用 Math.max 简化
        int left = Math.max(0, index - k + 1);
        int maxRight = Math.min(arr.length - 1, index + k);
        int right = left + k - 1;
        // 3. 计算初始窗口的差值绝对值之和
        int minDiffSum = 0;
        for (int i = left; i <= right; i++) {
            minDiffSum += Math.abs(arr[i] - x);
        }
        int minLeft = left;
        int curDiffSum = minDiffSum;
        // 4. 滑动窗口
        // 窗口向右滑动：移除 left-1，加入 right+1
        // 注意：原代码先 ++left 和 ++right 再进入循环，这里调整逻辑使其更符合常规滑动窗口写法，效果一致
        while (right < maxRight) {
            int nextRight = right + 1;
            int removeLeft = left;
            int lDiff = Math.abs(arr[removeLeft] - x);
            int rDiff = Math.abs(arr[nextRight] - x);
            curDiffSum = curDiffSum - lDiff + rDiff;
            if (curDiffSum < minDiffSum) {
                minDiffSum = curDiffSum;
                minLeft = removeLeft + 1;
            }
            // 移动窗口指针
            left++;
            right++;
        }
        // 5. 构建结果
        List<Integer> result = new ArrayList<>(k);
        for (int i = minLeft; i < minLeft + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }

    /**
     * 在有序数组中找到最后一个小于等于目标值的元素下标
     *
     * @param arr    升序排序的数组
     * @param target 目标值
     * @return 最后一个小于等于target的元素下标，如果所有元素都大于target，则返回0
     */
    private int findLastLessOrEqual(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        // 默认返回-1，表示未找到
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                // 当前元素小于等于目标值，可能是要找的
                result = mid;
                // 继续在右边寻找是否有更大的满足条件的元素
                left = mid + 1;
            } else {
                // 当前元素大于目标值，目标值一定在左边
                right = mid - 1;
            }
        }

        // 如果所有元素都大于target，返回第一个元素的下标
        return result == -1 ? 0 : result;
    }


    /**
     * findClosestElements的再优化：
     * 在findClosestElements()中，计算出左边界后，会向右一直遍历，至多遍历2*k个元素，即设小于x的最大元素下标为index，则遍历 index - k -> index + k区间内的所有可能区间
     * 继续优化：
     * 将arr分为两部分，前一部分所有元素 [0,left] 都小于 x，后一部分所有元素 [right,n−1] 都大于等于 x
     * 然后从left 和 right 开始扩张区间，如果 Math.abs(arr[left]-k) > Math.abs(arr[right]-x)，则说明右侧差值更小，向右扩展
     */
    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        int right = binarySearch(arr, x);
        int left = right - 1;
        while (k-- > 0) {
            if (left < 0) {
                right++;
            } else if (right >= arr.length) {
                left--;
            } else if (x - arr[left] <= arr[right] - x) {
                left--;
            } else {
                right++;
            }
        }
        List<Integer> ans = new ArrayList<Integer>();
        for (int i = left + 1; i < right; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }

    public int binarySearch(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * 直接按”最接近“这个规则进行排序，然后把前k个元素从小打到排序后返回
     * 时间复杂度：O(nlogn)，其中 n 是数组 arr 的长度。排序需要 O(nlogn)。
     * 空间复杂度：O(logn)。返回值不计算时间复杂度。排序需要 O(logn) 的栈空间。
     */
    public List<Integer> findClosestElements3(int[] arr, int k, int x) {
        if (k >= arr.length) {
            List<Integer> result = new ArrayList<>(arr.length);
            for (int num : arr) {
                result.add(num);
            }
            return result;
        }
        List<Integer> result = new ArrayList<>(arr.length);
        for (int num : arr) {
            result.add(num);
        }
        result.sort(Comparator.comparingInt(a -> Math.abs(a - x)));
        result = result.subList(0, k);
        result.sort(Comparator.comparingInt(a -> a));
        return result;
    }
}
