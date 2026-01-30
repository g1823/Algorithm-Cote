package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 875. 爱吃香蕉的珂珂
 */
public class MinEatingSpeed {

    /**
     * 思路：
     * 每次最少吃1根，最大一次把所有香蕉堆中数量最多的一堆吃完
     * 直接二分查找，初始区间为[1, max(piles)]
     * 每次都需要O(n)时间判断指定香蕉数mid能否在h时间内吃完
     */
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = Arrays.stream(piles).max().getAsInt();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (Arrays.stream(piles).map(p -> (p + mid - 1) / mid).sum() <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
