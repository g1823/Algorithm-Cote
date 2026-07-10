package leetCode.difficult;

import java.util.Arrays;

/**
 * @description: 902. 最大为 N 的数字组合 (数位DP dfs+记忆化搜索)
 */
public class AtMostNGivenDigitSet {
    /**
     * 缓存，记录没有前导0且不受限的状态下，后续的有效元素个数
     */
    int[] memo;
    /**
     * n的每一位数字
     */
    int[] nums;
    /**
     * 给定集合的每一位数字
     */
    int[] digitNums;

    public int atMostNGivenDigitSet(String[] digits, int n) {
        digitNums = new int[digits.length];
        for (int i = 0; i < digits.length; i++) {
            digitNums[i] = digits[i].charAt(0) - '0';
        }
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
        // 位数少于len(N)的数字，每一位可以任意选
        int m = digitNums.length;
        int res = 0;
        int pow = 1;
        for (int i = 1; i < length; i++) {
            pow *= m;
            res += pow;
        }
        memo = new int[length];
        Arrays.fill(memo, -1);
        // 单独计算位数等于len(N)的情况，题目说明了digits不含0，因此dfs内不会出现重复计算
        return res + dfs(0, true);
    }

    public int dfs(int cur, boolean isLimit) {
        if (cur == nums.length) {
            return 1;
        }
        if (!isLimit && memo[cur] != -1) {
            return memo[cur];
        }
        int up = isLimit ? nums[cur] : 9;
        int res = 0;
        for (int digitNum : digitNums) {
            if (digitNum > up) {
                break;
            }
            res += dfs(cur + 1, isLimit && digitNum == up);
        }
        if (!isLimit) {
            memo[cur] = res;
        }
        return res;
    }
}
