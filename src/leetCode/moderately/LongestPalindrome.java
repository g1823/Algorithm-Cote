package leetCode.moderately;

/**
 * @author: gj
 * @description: 5. 最长回文子串
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println(longestPalindrome4(s));
    }

    /**
     * 解题思路（中心扩展法 - 蛮力版）：
     * 1. 回文串的一个重要性质：它关于中心对称。
     * - 回文中心可以是一个字符（奇数长度回文）；
     * - 也可以是两个相邻字符（偶数长度回文）。
     * 2. 遍历字符串的每一个位置 i，将其作为回文中心。
     * - 从 i 向左右两侧同时扩展，直到左右字符不相等，得到一个最长奇数回文。
     * - 再判断 i 与 i+1 是否相同，如果相同，则以 i 和 i+1 为中心，继续向左右扩展，得到一个最长偶数回文。
     * 3. 每次扩展后，比较回文子串的长度是否大于当前最大值，若大于则更新最大回文子串。
     * 时间复杂度：O(n^2)，空间复杂度：O(1)。
     */
    public static String longestPalindrome(String s) {
        // 记录最长回文子串
        String maxString = "";
        char[] chars = s.toCharArray();
        // 枚举每一个字符，作为回文中心
        for (int i = 0; i < chars.length; i++) {
            // ---------- 奇数长度回文 ----------
            int length = 0;
            // 从中心 i 向两边扩展，直到左右字符不同
            for (int j = 1; i - j >= 0 && i + j < chars.length; j++) {
                if (chars[i - j] != chars[i + j]) {
                    break;
                }
                length++;
            }
            // 更新最大回文子串
            if (maxString.length() < length * 2 + 1) {
                maxString = s.substring(i - length, i + length + 1);
            }
            // ---------- 偶数长度回文 ----------
            if (i + 1 < chars.length && chars[i] == chars[i + 1]) {
                length = 0;
                // 从中心 (i, i+1) 向两边扩展
                for (int j = 1; i - j >= 0 && i + j + 1 < chars.length; j++) {
                    if (chars[i - j] != chars[i + j + 1]) {
                        break;
                    }
                    length++;
                }
                // 更新最大回文子串
                if (maxString.length() < length * 2 + 2) {
                    maxString = s.substring(i - length, i + length + 2);
                }
            }
        }
        return maxString;
    }


    /**
     * 解题思路（中心扩展优化版）：
     * 1. 在原始解法中，奇偶回文是分开处理的。
     * 优化思路：当遇到一段连续相同的字符时，可以把这一整段作为回文中心，统一处理奇偶两种情况。
     * 2. 遍历字符串的每一个位置 i：
     * - 先找到从 i 开始的一段连续相同字符，记为 sameLength。
     * - 将这一段连续相同字符作为回文中心，然后再从中心向左右扩展。
     * 3. 每次扩展后，更新最大回文子串。
     * 时间复杂度仍为 O(n^2)，但减少了一些不必要的重复扩展。
     */
    public static String longestPalindrome2(String s) {
        String maxString = "";
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int sameLength = 0;
            // 先找到与 chars[i] 相同的连续字符个数
            for (int j = 1; i + j < chars.length; j++) {
                if (chars[i + j] == chars[i]) {
                    sameLength++;
                } else {
                    break;
                }
            }
            // 将这段连续相同的字符整体作为回文中心
            int length = 0, i1 = sameLength + i;
            // 从中心向两边扩展
            for (int j = 1; i - j >= 0 && i1 + j < chars.length; j++) {
                if (chars[i - j] != chars[i1 + j]) {
                    break;
                }
                length++;
            }
            // 更新最大回文子串
            if (maxString.length() < length * 2 + sameLength + 1) {
                maxString = s.substring(i - length, i + sameLength + length + 1);
            }
            // 跳过这段连续相同的字符
            i += sameLength;
        }
        return maxString;
    }


    /**
     * 动态规划解法
     * dp[i][j] = true 标识从i 到 j 的子串是回文
     * 计算dp[i][i] 也就是从i到j是不是回文，其实取决于子串[i+1到j-1]是不是回文串
     * 当i-j<=2时，不存在内部串，因此需要特殊计算
     * - i-j = 0，说明是一个字符，肯定是回文串
     * - i-j = 1，说明只有两个字符，两个字符相等就是回文串
     * - i-j = 2，说明有三个字符，只需要外边两个字符相等(也就是i和j)就是回文串
     * 其他情况，当i-j>2时，此时就存在了内部串，也就是i+1和j-1不同了:
     * - dp[i][j] = dp[i+1][j-1] && s[i] == s[j]，即内部是回文串，i和j相等，那么i到j也是回文串
     * 状态转移方程：
     * dp[i][j] =  s[i] == s[j] && (i-j<=2 || dp[i+1][j-1])
     * 注意：
     * 计算dp[i][j]依赖于dp[i+1][j-1],意味着需要将i从大到小算，j从小到大算
     * 时间复杂度仍为 O(n^2)，空间复杂度仍为 O(n^2)，
     */
    public static String longestPalindrome3(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        char[] chars = s.toCharArray();
        int maxI = 0, maxJ = 0;
        // 自己到自己肯定是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        for (int i = n - 1; i >= 0; i--) {
            // j 从 i+1 开始,j<i成逆序计算了
            for (int j = i + 1; j < n; j++) {
                boolean r = chars[i] == chars[j] && (j - i <= 2 || dp[i + 1][j - 1]);
                dp[i][j] = r;
                // 如果是回文，且长度更长，则更新答案
                if (r && j - i > maxJ - maxI) {
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        return s.substring(maxI, maxJ + 1);
    }


    /**
     * Manacher算法
     * 思路：
     * 考虑如下情况，索引i位置的回文串单边长度为l，遍历到j(j>i)时，如果j<i+l，也就是j在以i为中心的回文串内
     * 那么根据回文的对称性，可以直到在i的左侧存在一个k，跟j对称，j和k局部字符串是一模一样的。
     * 计算k: k = i - (j - i) = 2i - j
     * 如果直到以k为中心的回文长度 lk ，那么这个lk 可以被j参考：
     * - lk + j < i + l ，即j的回文最远处也落在了i的回文边界内，那么j的回文最远距离就是k的回文最远距离了
     * - lk + j >= i + l ，即j的回文最远处也落在了i的回文边界外，那么j的回文最远距离最小都是l-(j-i)了
     * 根据上面这个性质，可以借助前面已经计算过的回文串，维护一个当前有边界最靠右的回文串中心和长度，实现o(n)的遍历
     * 步骤：
     * 1、通过给原字符串字符之间插入#，使得所有串都变为奇数情况
     * 2、维护一个每个元素的最大回文长度p[]，当前回文边界最靠右的回文中心maxCenter，当前最靠右的回文右边界 maxRight
     * 3、遍历字符串，计算每个元素i的回文长度，并更新maxCenter，maxRight，p[i]
     * 时间复杂度 O(n)
     */
    public static String longestPalindrome4(String s) {
        char[] chars = s.toCharArray();
        /**
         * 为了避免奇偶性区分，把字符串转成统一形式：
         * 在每个字符之间和首尾加特殊分隔符（如 #），并在开头结尾加哨兵字符。
         * 例如："abba" → ^#a#b#b#a#$
         * 好处：
         * 所有回文都是 奇数长度；
         * 不用分奇偶情况；
         * 哨兵 ^ 和 $ 防止越界。
         */
        char[] t = new char[2 * chars.length + 3];
        t[0] = '^';
        t[t.length - 1] = '$';
        t[t.length - 2] = '#';
        for (int i = 0; i < chars.length; i++) {
            t[2 * i + 2] = chars[i];
            t[2 * i + 1] = '#';
        }
        // 当前边界最靠右的回文中心和回文右边界
        int maxCenter = 0, maxRight = 0;
        int[] p = new int[t.length];
        for (int i = 1; i < t.length; i++) {
            // 也就是i关于maxCenter的对称点 maxCenter - ( i - maxCenter)
            int j = 2 * maxCenter - i;
            // 如果i在边界内
            if (i < maxRight) {
                // 对称点的回文长度小于最右回文中心，也就是当前i的回文长度小于最右回文长度，在边界内
                if (i + p[j] < maxRight) {
                    p[i] = p[j];
                    continue;
                } else {
                    // 对称点的回文长度大于边界，那么当前i的最小回文长度是有边界maxRight - i，后面再继续扩展
                    p[i] = maxRight - i;
                }
            } else {
                p[i] = 0;
            }
            // 当前i扩展后的长度小于数组长度就可以一直扩展
            while (i + p[i] < t.length - 1 && i - p[i] > 0 && t[i + p[i] + 1] == t[i - p[i] - 1]) {
                p[i]++;
            }
            // 如果当前i的回文右边界超过了最右回文右边界，更新最右回文中心
            if (i + p[i] > maxRight) {
                maxCenter = i;
                maxRight = i + p[i];
            }
        }
        // 遍历回文长度数组，获取最大回文长度及回文中心索引
        int maxLen = 0, centerIndex = 0;
        for (int i = 1; i < t.length - 1; i++) {
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        // 4. 提取结果
        int start = (centerIndex - maxLen) / 2;
        // 映射回原字符串位置
        return s.substring(start, start + maxLen);
    }
}
