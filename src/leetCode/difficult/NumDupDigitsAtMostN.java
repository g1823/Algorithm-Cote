package leetCode.difficult;

import java.util.Arrays;

/**
 * @description: 1012. 至少有 1 位重复的数字 (数位DP dfs+记忆化搜索)
 */
public class NumDupDigitsAtMostN {
    /**
     * 缓存，记录没有前导0且不受限的状态下，后续的有效元素个数
     * 格式：memo[cur][bitMask] = 表示当前第cur位，从最高位到当前位已经出现的数字状态位bitMask时，后续有效数字的个数
     */
    int[][] memo;
    /**
     * n的每一位数字
     */
    int[] nums;

    public int numDupDigitsAtMostN(int n) {
        // 提取n的十进制各位数字（高位在前）
        int[] temp = new int[32];
        int length = 0, t = n;
        while (t > 0) {
            temp[length++] = t % 10;
            t /= 10;
        }
        nums = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            nums[i] = temp[length - 1 - i];
        }
        memo = new int[length][1 << 10];
        for (int i = 0; i < length; i++) {
            Arrays.fill(memo[i], -1);
        }
        // 通过递归计算没有重复元素的数字的个数，然后用总数减去即可得到至少重复一个的
        // 初始时没有数字，则bitMask = 0，表示每一位都未出现
        return n - dfs(0, 0, true, true);
    }

    public int dfs(int cur, int bitMask, boolean isLimit, boolean leadZero) {
        if (cur == nums.length) {
            // 全前导0路径代表数字0，不在[1, n]范围内，不计入
            return leadZero ? 0 : 1;
        }
        if (!isLimit && !leadZero && memo[cur][bitMask] != -1) {
            return memo[cur][bitMask];
        }
        int up = isLimit ? nums[cur] : 9;
        int res = 0;
        for (int i = 0; i <= up; i++) {
            if ((bitMask & (1 << i)) != 0) {
                continue;
            }
            // 当前仍处于前导0阶段且选了0 → 不标记mask，继续前导0状态
            int nextMask = (leadZero && i == 0) ? bitMask : (bitMask | (1 << i));
            res += dfs(cur + 1, nextMask, isLimit && (i == up), leadZero && (i == 0));
        }
        if (!isLimit && !leadZero) {
            memo[cur][bitMask] = res;
        }
        return res;
    }
}
