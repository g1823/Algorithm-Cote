package leetCode.simple;

/**
 * @author: gj
 * @description: 28. 找出字符串中第一个匹配项的下标
 */
public class StrStr {

    public static void main(String[] args) {
        String s1 = "sadbutsad";
        String s2 = "sad";
        System.out.println(new StrStr().strStr(s1, s2));
    }

    public int strStr(String haystack, String needle) {
        return kmp(haystack, needle);
    }

    public int kmp(String haystack, String needle) {
        int m = haystack.length();
        int n = needle.length();
        if (m < n) {
            return -1;
        }
        int[] next = getNext(needle);
        int j = 0;
        for (int i = 0; i < m; i++) {
            // 不匹配就回退
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            // 匹配就往前走
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            // 如果模式串完全匹配
            if (j == n) {
                return i - n + 1;
            }
        }
        return -1;
    }

    /**
     * 计算模式串的 next 数组
     * 含义：next[i] 表示模式串中 [0..i] 这一段的最长相等前后缀的长度。
     * 换句话说，next[i] = k 表示：needle[0..k-1] == needle[i-k+1..i]
     * 在 KMP 匹配过程中，如果模式串在 i 位置失配，
     * 就可以把 j 回退到 next[j-1]，避免主串回退。
     */
    public int[] getNext(String needle) {
        int n = needle.length();
        int[] next = new int[n];
        char[] chars = needle.toCharArray();
        // 当前前缀长度
        int j = 0;
        for (int i = 1; i < n; i++) {
            // 如果失配，就尝试缩短前缀，回退到更短的相等前后缀
            while (j > 0 && chars[i] != chars[j]) {
                j = next[j - 1];
            }
            // 如果匹配，前缀长度加一
            if (chars[i] == chars[j]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

}
