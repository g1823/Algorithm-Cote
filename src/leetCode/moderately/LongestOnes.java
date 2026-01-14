package leetCode.moderately;

/**
 * @author: gj
 * @description: 1004. 最大连续1的个数 III
 */
public class LongestOnes {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        System.out.println(new LongestOnes().longestOnes(nums, 2));
    }

    /**
     * 滑动窗口
     * 解题思路：
     * 本题允许将最多 k 个 0 翻转为 1，等价于：
     * —— 在数组中寻找一个最长的连续子数组，使得其中 0 的数量不超过 k。
     * 这是一个典型的「滑动窗口」问题：
     * 1. 使用两个指针 left、right 表示当前窗口 [left, right]
     * 2. 用 zeroCount 统计窗口内 0 的数量
     * 3. 当 zeroCount <= k 时，窗口合法，可以更新答案
     * 4. 当 zeroCount > k 时，窗口不合法，需要移动 left 收缩窗口
     * 合法性说明：
     * 窗口内0数量超过k，意味着是新增元素为0，那么就无法与后续元素连续起来，因此想要更大，只能去掉窗口最左侧的0
     * 关键点：
     * - 窗口长度可以通过 right - left + 1 直接计算
     * - 不需要额外维护窗口长度变量
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * 注意：
     * - 尽量只维护“约束变量”，不要维护“结果变量”
     * - 最开始通过一个遍历记录的当前窗口内的最大值，然而这是没有必要的，还需要不断维护这个值
     */
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int zeroCount = 0;
        int maxLen = 0;
        for (int right = 0; right < nums.length; right++) {
            // 扩展窗口：右指针向右移动
            if (nums[right] == 0) {
                zeroCount++;
            }
            // 若窗口中 0 的数量超过 k，收缩窗口
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            // 此时窗口合法，更新最大长度
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
