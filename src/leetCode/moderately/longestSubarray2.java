package leetCode.moderately;

/**
 * @author: gj
 * @description: 1493. 删掉一个元素以后全为 1 的最长子数组
 */
public class longestSubarray2 {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1};
        System.out.println(new longestSubarray2().longestSubarray(nums));
    }

    /**
     * 滑动窗口:
     * 使用滑动窗口维护一个最多包含一个 0 的子数组。
     * 当窗口内 0 的数量超过 1 时，移动左指针缩小窗口。
     * 由于必须删除一个元素，所以最终的有效长度是窗口长度减 1。
     */
    public int longestSubarray(int[] nums) {
        int left = 0;
        int zeroCount = 0;
        int maxLen = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            // 如果0的数量超过1，移动左指针
            while (zeroCount > 1) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            // 更新最大长度
            // 窗口长度是 right-left+1，但必须删一个元素
            // 所以实际长度是 (right-left+1) - 1 = right-left
            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }
}
