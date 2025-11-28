package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 274. H 指数
 */
public class HIndex {

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5};
        System.out.println(new HIndex().hIndex2(citations));
    }

    /**
     * 复杂度n*log n
     * 这个本质上是判断一个数组中大于等于当前元素的数量，取最大值。
     * 可以直接排序(从小到大)，那么比citations[i]大的元素下标都大于i,比其大的数量就是n-i
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        int max = 0;
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= n - i) {
                max = Math.max(max, n - i);
            }
        }
        return max;
    }

    /**
     * 复杂度n
     * 根据题目理解，可以得知，H指数不可能大于数组的长度
     * 对于引用次数大于数组长度的数字，可以累加到t[n]上
     * 然后倒序遍历，记录比当前元素大的元素数量，若当前元素值大于等于比当前元素大的数量，则更新最大值
     */
    public int hIndex2(int[] citations) {
        int n = citations.length;
        int[] t = new int[n + 1];
        for (int citation : citations) {
            if (citation > n) {
                t[n]++;
            } else {
                t[citation]++;
            }
        }
        int cur = 0, result = 0;
        for (int i = t.length - 1; i >= 0; i--) {
            cur += t[i];
            if (cur >= i) {
                result = Math.max(result, i);
            }
        }
        return result;
    }

    /**
     * 二分查找：复杂度为n*log n
     * 1. 确定二分查找的边界，数组长度为n，那么h指数范围是[0,n]
     * 2. 那么答案一定在这些数字之中，可以进行二分查找
     * 3. 每次二分确定一个数mid，然后遍历数组，判断是否至少有 mid 个数大于 mid。如果有，说明h指数大于等于mid，说明要寻找的 h 在搜索区间的右边，反之则在左边。
     */
    public int hIndex3(int[] citations) {
        int n = citations.length;
        int left = 0, right = n;
        while (left < right) {
            // 加1防止只有一个元素死循环
            int mid = (left + right + 1) >> 1;
            int count = 0;
            for (int citation : citations) {
                if (citation >= mid) {
                    count++;
                }
            }
            if (count >= mid) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
