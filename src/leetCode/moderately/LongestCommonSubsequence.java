package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 1143. 最长公共子序列
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String text1 = "oxcpqrsvwf";
        String test2 = "shmtulqrypy";
        System.out.println(new LongestCommonSubsequence().longestCommonSubsequence2(text1, test2));
    }

    int res = 0;

    /**
     * 解法思路（DFS + 剪枝）：
     * 1. 暴力枚举思路：
     * - 最长公共子序列（LCS）的定义是：在两个字符串中找到相同的子序列，且相对顺序不变。
     * - 最直观的解法是 DFS/回溯：从 text1 的某个位置开始，尝试匹配 text2 中的后续字符，
     * 枚举所有可能的公共子序列，取最长值。
     * - 这种方法可以保证正确性，但最坏情况下复杂度是指数级，会超时。
     * 2. 剪枝优化的思考：
     * - 如果 text1 剩余的字符数 + 当前匹配数 <= 已知最大结果 res，说明即使把剩余字符都匹配上，
     * 也不可能超过 res，可以提前终止递归。
     * - 这个优化能显著减少无效搜索。
     * 3. 快速定位下一个匹配位置：
     * - 在 DFS 中，如果我们直接用 while 去扫描 text2 是否含有某个字符，会导致频繁 O(n) 查找，
     * 且可能出现死循环或数组越界。
     * - 为了优化，预处理一个 nextPos 表：
     * nextPos[i][c] = 从 text2 的下标 i 开始，字符 c 第一次出现的位置（不存在时为 -1）。
     * 这样在 DFS 中，我们可以 O(1) 跳到 text2 中下一个目标字符的位置。
     * 4. 搜索过程：
     * - 从 text1 的第 i 个字符开始，枚举后续字符 text1[k]。
     * - 通过 nextPos[j][text1[k]] 查找 text2 中匹配的下标位置 pos。
     * - 如果存在匹配，则递归调用，进入下一层（i = k+1, j = pos+1, count+1）。
     * - 每次递归更新全局最大值 res。
     * 5. 总结：
     * - 该方法保留了暴力 DFS 的直观思路，通过预处理 nextPos 和剪枝大大减少了搜索空间。
     * - 适合小中等规模输入，可以跑通样例，结果正确。
     * - 但最坏情况下复杂度仍然接近指数级，因此无法高效处理超大输入。
     * - 在工程上真正可行的解法仍是 DP（O(mn)），但这个 DFS 写法有助于理解问题本质。
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();

        // 预处理 nextPos：nextPos[i][c] 表示从 text2 的位置 i 往后，第一个字符 c 出现的位置
        int[][] nextPos = new int[n + 1][26];
        for (int c = 0; c < 26; c++) {
            // 越界后都不存在
            nextPos[n][c] = -1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int c = 0; c < 26; c++) {
                // 继承后面的结果
                nextPos[i][c] = nextPos[i + 1][c];
            }
            // 当前字符更新
            nextPos[i][text2.charAt(i) - 'a'] = i;
        }

        dfs(text1, text2, 0, 0, 0, nextPos);
        return res;
    }

    private void dfs(String text1, String text2, int i, int j, int count, int[][] nextPos) {
        // 更新全局最大值
        res = Math.max(res, count);

        // 剪枝：剩余字符数不足以超过 res，直接返回
        if (text1.length() - i + count <= res) return;

        for (int k = i; k < text1.length(); k++) {
            int c = text1.charAt(k) - 'a';
            // 在 text2[j..end] 中找到 c 的下一个位置
            int pos = nextPos[j][c];
            // text2 后面没有这个字符了
            if (pos == -1) {
                continue;
            }
            // 递归：匹配到一个字符
            dfs(text1, text2, k + 1, pos + 1, count + 1, nextPos);
        }
    }


    /**
     * 方法：动态规划
     * 一、从回溯到动态规划的思路演变：
     * 1. 最初思路是回溯/DFS：
     * - 对于 text1[i..] 和 text2[j..]，我们尝试：
     * a) 如果 s1[i] == s2[j]，则这个字符属于 LCS，递归子问题 (i+1, j+1)。
     * b) 如果 s1[i] != s2[j]，则可以选择跳过 text1[i] 或 text2[j]，递归子问题 (i+1, j) 或 (i, j+1)。
     * - 递归公式：
     * LCS(i, j) = (s1[i] == s2[j]) ? 1 + LCS(i+1, j+1)
     * - 递归终止条件：当 i == m 或 j == n 时，返回 0。
     * 2. 回溯问题：重复子问题导致指数级复杂度。
     * - 举例：LCS(2, 3) 可能在不同递归分支中被重复计算多次。
     * - 因此需要“记忆化”避免重复计算，这就是动态规划的核心思想。
     * 3. 动态规划转化：
     * - 定义 dp[i][j] = s1[i..end] 和 s2[j..end] 的最长公共子序列长度。
     * - 状态转移公式：
     * if (s1[i] == s2[j]) dp[i][j] = 1 + dp[i+1][j+1];
     * else dp[i][j] = max(dp[i+1][j], dp[i][j+1]);
     * - 边界条件：dp[m][*] = 0, dp[*][n] = 0（任意一方为空串时 LCS 长度为 0）。
     * 二、算法复杂度：
     * - 时间复杂度：O(m * n)，每个状态只计算一次。
     * - 空间复杂度：O(m * n)，可进一步优化到 O(min(m, n))。
     * 三、总结：
     * - 本解法通过自底向上的 DP，消除了回溯中的重复计算，使得求解 LCS 从指数级优化到多项式时间。
     * - 返回结果 dp[0][0] 即为 text1 和 text2 的 LCS 长度。
     */
    public int longestCommonSubsequence2(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        // 将字符串转换为字符数组，避免频繁调用 charAt
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        // dp[i][j] 表示 s1[i..end] 和 s2[j..end] 的 LCS 长度
        int[][] dp = new int[m + 1][n + 1];
        // 从后往前填表，因为 dp[i][j] 依赖 dp[i+1][*]
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // 如果当前字符相等，则这个字符可以作为 LCS 的一部分
                if (s1[i] == s2[j]) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                }
                // 否则，取跳过 s1[i] 或跳过 s2[j] 的最大值
                else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        // dp[0][0] 就是完整字符串的 LCS 长度
        return dp[0][0];
    }
}
