package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 2411. 按位或最大的最小子数组长度
 */
public class SmallestSubarrays {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2};
        System.out.println(Arrays.toString(new SmallestSubarrays().smallestSubarrays(nums)));
    }


    /**
     * 解题思路：
     * 本题不要求每个子数组的按位或必须等于全局最大值，而是求对于每个下标 i，
     * 以 i 开头的最短子数组，其按位或结果等于能从 i 开始往后所有子数组中得到的最大值。
     * 实现方式：
     * 从后往前遍历数组，每次更新当前数字中每一位为 1 的 bit 的最新出现位置（bitPos[j] 表示第 j 位为 1 的最远下标）。
     * 然后找出所有 bitPos[j] 的最大值 maxRight，表示从当前下标 i 开始必须延伸到 maxRight 才能包含所有必要的 bit。
     * 子数组的最小长度即为 maxRight - i + 1。
     */
    public int[] smallestSubarrays2(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int[] bitPos = new int[32];
        Arrays.fill(bitPos, -1);

        for (int i = n - 1; i >= 0; i--) {
            // 更新当前位置 i 的每一位 bit 的最后出现位置
            for (int j = 0; j < 32; j++) {
                if (((nums[i] >> j) & 1) == 1) {
                    bitPos[j] = i;
                }
            }

            // 找出所有 bit 对应的最远右边界
            int maxRight = i;
            for (int j = 0; j < 32; j++) {
                if (bitPos[j] != -1) {
                    maxRight = Math.max(maxRight, bitPos[j]);
                }
            }

            res[i] = maxRight - i + 1;
        }

        return res;
    }

    /**
     * 这里思路有问题，求取的是每个下标达到全数组最大或运算的最小长度，而本题目只要求求相对最大
     * 解题思路：
     * 1️⃣ 首先明确题意：对于每一个下标 i，我们要找到从 i 开始的最短子数组，使得该子数组的按位或结果为整个数组的最大按位或值。
     * 2️⃣ 第一步：求出整个数组的最大按位或 maxOr
     * - 因为我们只有知道最大值，才能判断一个子数组是否达标。
     * - 使用一个变量遍历整个数组进行按位或即可。
     * 3️⃣ 第二步（暴力思路）：从每个下标 i 开始，往后滑动并进行按位或操作，直到等于 maxOr 为止，记录最短长度。
     * - 缺点：暴力法时间复杂度为 O(n^2)，会重复大量计算（如重复计算 i 到 k 的 or 值）。
     * - 且由于按位或的不可逆性，不能从上一个结果中“减去” nums[i] 的影响。
     * 4️⃣ 第三步（优化思路）：从后往前遍历 + 按位思考
     * - 每一个整数最多有 32 位，因此我们可以从后往前记录每一位（bit）为 1 的“最远出现位置”。
     * - 使用一个长度为 32 的数组 bitPos[] 来维护这些位的最远位置。
     * - 对于当前位置 i 的 nums[i]，我们更新 bitPos 中为 1 的位。
     * 5️⃣ 判断当前位置是否能够构成 maxOr：
     * - 对于 maxOr 中为 1 的每一位，检查 bitPos[b] 是否已经被更新（即不是 -1）
     * - 若任意一位 maxOr 的 bit 在 bitPos 中为 -1，说明从当前位置 i 往后都无法构成 maxOr。
     * - 否则，我们在 bitPos 中找到最大位置 maxRight，表示从 i 开始需要至少扩展到该位置才能包含 maxOr 的所有位。
     * 6️⃣ 时间复杂度为 O(n * 32) ≈ O(n)，空间复杂度 O(1)（常数级）
     */

    public int[] smallestSubarrays(int[] nums) {
        int[] maxOrs = new int[nums.length];
        int maxOr = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            maxOr = maxOr | nums[i];
            maxOrs[i] = maxOr;
        }
        boolean flag = false;
        int[] res = new int[nums.length];
        int[] bitPos = new int[32];
        Arrays.fill(bitPos, -1);
        for (int i = nums.length - 1; i >= 0; i--) {
            // 计算当前i的每一位的bit，并更新bitPos
            int maxRight = -1;
            for (int j = 0; j < 32; j++) {
                int bit = (nums[i] >> j) & 1;
                if (bit == 1) {
                    bitPos[j] = i;
                }
                maxRight = Math.max(maxRight, bitPos[j]);
            }
            if (flag) {
                res[i] = maxRight - i + 1;
            } else {
                int currentMaxOr = maxOrs[i];
                boolean f = true;
                for (int j = 0; j < 32; j++) {
                    int bit = (currentMaxOr >> j) & 1;
                    if (bit == 1 && bitPos[j] == -1) {
                        f = false;
                        break;
                    }
                }
                if (f) {
                    if (currentMaxOr == maxOr) {
                        flag = true;
                    }

                } else {
                    res[i] = 0;
                }
            }
        }
        return res;
    }
}
