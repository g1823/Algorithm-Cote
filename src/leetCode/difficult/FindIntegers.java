package leetCode.difficult;

import java.util.Arrays;

/**
 * @description: 600. 不含连续1的非负整数 (数位DP dfs+记忆化搜索)
 */
public class FindIntegers {

    /**
     * memo[i][j] = k表示当前第i个元素，上一个元素为j(0或1)时，后续的有效元素个数为k
     */
    int[][] memo;
    /**
     * 记录n的二进制表示
     */
    int[] nums;

    public int findIntegers(int n) {
        // n = 0时，只有0一个有效元素
        if (n == 0) {
            return 1;
        }
        // 计算有效长度（去除前导0），并记录n的二进制表示
        int[] temp = new int[32];
        int length = 0, t = n;
        while (t > 0) {
            temp[length++] = t & 1;
            t >>= 1;
        }
        nums = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            nums[i] = temp[length - 1 - i];
        }
        memo = new int[length][2];
        for (int i = 0; i < length; i++) {
            Arrays.fill(memo[i], -1);
        }
        // 从最高位(第0位)开始计算，上一位认为是0，前导0为true
        return dfs(0, 0, true, true);
    }

    public int dfs(int index, int last, boolean isLimit, boolean leadZero) {
        // 递归终止条件：index越界，表示已经处理完所有元素（上一个合法才能到达下一个元素，因此直接返回1，表示有一个元素）
        if (index == nums.length) {
            return 1;
        }
        // 不受限且没有前导0，存在缓存直接返回缓存中的值
        if (!isLimit && !leadZero && memo[index][last] != -1) {
            return memo[index][last];
        }
        // 如果当前位受限，则上界为当前位值，否则为1
        int up = isLimit ? nums[index] : 1;
        int res = 0;
        for (int i = 0; i <= up; i++) {
            // 不能存在连续1，如果上一位为1，当前位也是1，则不合法，则跳过
            if (i == 1 && last == 1) {
                continue;
            }
            res += dfs(index + 1, i, isLimit && i == up, leadZero && i == 0);
        }
        if (!isLimit && !leadZero) {
            memo[index][last] = res;
        }
        return res;
    }
}
