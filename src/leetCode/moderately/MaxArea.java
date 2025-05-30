package leetCode.moderately;

/**
 * @author: gj
 * @description: 11. 盛最多水的容器
 */
public class MaxArea {

    /**
     * 蛮力(超时)
     *
     * @param height 高度
     * @return 最大区域
     */
    public static int maxArea(int[] height) {
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = Math.min(height[i], height[j]) * (j - i);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }


    /**
     * 双指针解法（Two Pointers）
     * <p>
     * 思路说明：
     * 本问题本质上是寻找两个柱子，使得它们之间能形成最大面积的容器：面积 = min(height[i], height[j]) * (j - i)。
     * 蛮力法是枚举所有 i < j 的组合，时间复杂度为 O(n²)。
     * <p>
     * 为了优化，我们使用双指针法：
     * - 初始化两个指针，left = 0，right = height.length - 1。
     * - 每次计算当前区域的面积，并尝试更新最大面积。
     * - 为了尝试更大的面积，移动较短的那根柱子（因为高度受限于较短的一方），因为移动较高的柱子无法增加高度，反而减少宽度，面积一定减小，而移动较矮的那根柱子，有可能在高度上“变大”，尽可能补偿宽度减小的损失。
     * - 如果 height[left] < height[right]，说明左边是短板，移动 left 向右。
     * - 否则，移动 right 向左。
     * <p>
     * 这样能在 O(n) 的时间内遍历所有可能的最大面积组合，避免了无意义的枚举。
     *
     * @param height 高度数组
     * @return 能容纳的最大水量
     */
    public static int maxArea2(int[] height) {
        int maxArea = 0, l = 0, r = height.length - 1;
        while (l < r) {
            maxArea = Math.max(maxArea, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return maxArea;
    }

    /**
     * maxArea2双指针解法的优化，对双指针法的进一步剪枝，进行高度跳跃优化
     * 在标准双指针法中，我们每次只移动一位指针。
     * 但如果我们知道当前最小高度为 min = height[l]（假设左侧较小），并且下一次 height[l+1] <= min，那么面积一定会更小，
     * 因为：宽度减少了 r - (l + 1) < r - l；高度没有变高，甚至更矮。
     * 因此可以跳过这些不会产生更大面积的情况，直接跳到下一个比 min 更高的柱子
     *
     * @param height 高度数组
     * @return 能容纳的最大水量
     */
    public static int maxArea3(int[] height) {
        int maxArea = 0, l = 0, r = height.length - 1;
        while (l < r) {
            int min = Math.min(height[l], height[r]);
            maxArea = Math.max(maxArea, min * (r - l));

            if (height[l] < height[r]) {
                while (l < r && height[l] <= min) {
                    l++;
                }
            } else {
                while (l < r && height[r] <= min) {
                    r--;
                }
            }
        }
        return maxArea;
    }
}
