package leetCode.moderately;

/**
 * @author: gj
 * @description: 162. 寻找峰值
 */
public class FindPeakElement {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        System.out.println(new FindPeakElement().findPeakElement(nums));
    }

    /**
     * 思路1：时间复杂度O(n),空间复杂度O(1)
     * - 可以遍历数组，只要发现一个峰值则返回。
     * 思路2：时间复杂度O(n),空间复杂度O(1)
     * - 如果我们从一个位置开始，不断地向高处走，那么最终一定可以到达一个峰值位置。
     * - 因此，我们首先在 [0,n) 的范围内随机一个初始位置 i，随后根据 nums[i−1],nums[i],nums[i+1] 三者的关系决定向哪个方向走：
     * -    如果 nums[i−1]<nums[i]>nums[i+1]，那么位置 i 就是峰值位置，我们可以直接返回 i 作为答案；
     * -    如果 nums[i−1]<nums[i]<nums[i+1]，那么位置 i 处于上坡，我们需要往右走，即 i←i+1；
     * -    如果 nums[i−1]>nums[i]>nums[i+1]，那么位置 i 处于下坡，我们需要往左走，即 i←i−1；
     * -    如果 nums[i−1]>nums[i]<nums[i+1]，那么位置 i 位于山谷，两侧都是上坡，我们可以朝任意方向走。
     * - 如果我们规定对于最后一种情况往右走，那么当位置 i 不是峰值位置时：
     * -    如果 nums[i]<nums[i+1]，那么我们往右走；
     * -    如果 nums[i]>nums[i+1]，那么我们往左走。
     * - 思路3：对于思路2而言，如果 nums[i]<nums[i+1]，并且我们从位置 i 向右走到了位置 i+1，那么位置 i 左侧的所有位置是不可能在后续的迭代中走到的。
     * 因此可以使用二分思想进行优化：
     * 对于当前可行的下标范围 [l,r]，我们随机一个下标 i；
     * - 如果下标 i 是峰值，我们返回 i 作为答案；
     * - 如果 nums[i]<nums[i+1]，那么我们抛弃 [l,i] 的范围，在剩余 [i+1,r] 的范围内继续随机选取下标；
     * - 如果 nums[i]>nums[i+1]，那么我们抛弃 [i,r] 的范围，在剩余 [l,i−1] 的范围内继续随机选取下标。
     */
    public int findPeakElement(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int m = l + (r - l) / 2;

            // 如果右边比左边高，往右边爬
            if (nums[m] < nums[m + 1]) {
                l = m + 1;
            } else {
                // 否则往左边爬
                r = m;
            }
        }
        return l;
    }
}
