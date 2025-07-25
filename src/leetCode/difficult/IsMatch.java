package leetCode.difficult;

/**
 * @author: gj
 * @description: 10. 正则表达式匹配
 */
public class IsMatch {

    public static void main(String[] args) {
        String s = "aa";
        String p = "a";
        System.out.println(new IsMatch().isMatch3(s, p));
    }

    /**
     * 正则匹配：记忆化搜索（基于递归回溯）
     * 思路：
     * 每次尝试从 s[i] 和 p[j] 开始匹配：
     * - 如果 p[j + 1] 是 '*'，代表前一个字符可重复 0 次或多次。
     * - - 匹配 0 次：跳过当前字符和 *，即 dfs(i, j + 2)
     * - - 匹配 ≥1 次：如果当前字符匹配，递归 dfs(i + 1, j) 尝试继续匹配
     * - 如果 p[j + 1] 不是 '*'，则当前字符必须匹配（包括 . 的通配），再继续 dfs(i + 1, j + 1)
     * 终止条件：
     * - 当 j >= p.length()，即正则串用完，返回 i == s.length() 判断原串是否也用完
     */
    public boolean isMatch(String s, String p) {
        return dfs(s, p, 0, 0);
    }

    public boolean dfs(String s, String p, int i, int j) {
        // 递归终止条件,由于j会跳跃(j+=2)，因此需要用j来判断递归终止条件
        if (j >= p.length()) {
            return i == s.length();
        }
        boolean isMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            return dfs(s, p, i, j + 2) || (isMatch && dfs(s, p, i + 1, j));
        } else {
            return isMatch && dfs(s, p, i + 1, j + 1);
        }
    }

    /**
     * isMatch优化：
     * 当 s = "aaaaa" , p = "a*a*a*a*a"时，递归会重复计算很多次：
     * 当 p的第一个a去匹配时，会走两个分支，第一个a使用0次 和 1次，而这两个分支向后走时，都会对p的第二个a再次进行选择使用0,1,2,3...次，第三个a同理，存在了大量的重复计算。
     * 记录子问题 dfs(i, j) 的结果：表示 s[i:] 是否能匹配 p[j:]，避免重复计算
     * s[i:] 表示：字符串 s 从下标 i 开始到结尾的子串
     * 例如：s = "abcde"，则 s[2:] = "cde"
     * p[j:] 表示：字符串 p 从下标 j 开始到结尾的子串
     * 例如：p = "a*b*c"，p[2:] = "b*c"
     */
    public boolean isMatch2(String s, String p) {
        Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
        return dfs2(s, p, 0, 0, memo);
    }

    public boolean dfs2(String s, String p, int i, int j, Boolean[][] memo) {
        // 递归终止条件,由于j会跳跃(j+=2)，因此需要用j来判断递归终止条件
        if (j >= p.length()) {
            return i == s.length();
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        boolean isMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        boolean result = false;
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            result = dfs2(s, p, i, j + 2, memo) || (isMatch && dfs2(s, p, i + 1, j, memo));
        } else {
            result = isMatch && dfs2(s, p, i + 1, j + 1, memo);
        }
        memo[i][j] = result;
        return result;
    }


    /**
     * 动态规划：
     * isMatch2中，进行记忆化递归，记录了子问题 dfs(i, j) 的结果：表示 s[i:] 是否能匹配 p[j:]
     * 这里可以采用动态规划避免栈太多，对比一下：
     * - 记忆化是 “我要这个结果 -> 递归去要它”
     * - 动规是 “我把所有可能结果先算好 -> 等着你来查”
     * 我们使用dp[i][j]来表示 s串的前i个字符是否可以匹配p串的前j个字符，即s[0,1,..i-1]是否匹配p[0,1,...,j-1]
     * 状态转移方程：
     * - 当p[j-1] == '*'时，需要考虑*前字符匹配0次到n次的情况
     * - - 匹配0次，dp[i][j] = dp[i][j-2]：表示p串的*和*前一个字符直接不使用，那么dp[i][j] = s[0,1,..i-1]是否匹配p[0,1,...,j-3]
     * - - 匹配多次，匹配多次的前提是p串的*前一个字符 能够匹配上 s串的当前字符，即 p[j-2] == '.' || p[j-2] == s[i-1]
     * - 当p[j-1] != '*'时
     * - - 需要上一个字符匹配以及当前字符匹配：dp[i][j] = dp[i - 1][j - 1] && (p[j-1] == s[i-1] || pc == '.');
     */
    public boolean isMatch3(String s, String p) {
        int sLength = s.length(), pLength = p.length();
        boolean[][] dp = new boolean[sLength + 1][pLength + 1];
        // 初始化：两个均为空串时可以直接匹配
        dp[0][0] = true;
        // 避免s为空串，而p为"x*"这种可以出现0次的字符串，需要初始化
        for (int j = 2; j <= pLength; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            } else {
                break;
            }
        }
        // s串每次向后移动一位
        for (int i = 1; i <= sLength; i++) {
            for (int j = 1; j <= pLength; j++) {
                char pc = p.charAt(j - 1);
                char sc = s.charAt(i - 1);
                // p串当前字符为*，特殊处理匹配0次到n次
                if (pc == '*') {
                    // 当前串取0次是否匹配
                    boolean isMatch = dp[i][j - 2];
                    // 当前串取n次是否匹配，s串的上一个字符和p串*前的一个字符相等 或 p串*前字符为“.”，说明上一个字符可以匹配，可以判断当前是否匹配
                    if (sc == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        isMatch |= dp[i - 1][j];
                    }
                    dp[i][j] = isMatch;
                } else {
                    // 上一个字符匹配且当前字符匹配才可
                    dp[i][j] = dp[i - 1][j - 1] && (sc == pc || pc == '.');
                }
            }
        }
        return dp[sLength][pLength];
    }
}
