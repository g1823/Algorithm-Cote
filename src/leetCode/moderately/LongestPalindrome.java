package leetCode.moderately;

/**
 * @author: gj
 * @description: 5. 最长回文子串
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println(longestPalindrome2(s));
    }

    /**
     * 蛮力
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        String maxString = "";
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            // 回文中心为1个，i为回文中心
            int length = 0;
            for (int j = 1; i - j >= 0 && i + j < chars.length; j++) {
                if (chars[i - j] != chars[i + j]) {
                    break;
                }
                length++;
            }
            if (maxString.length() < length * 2 + 1) {
                maxString = s.substring(i - length, i + length + 1);
            }
            // 回文中心2个，i和i+1为回文中心
            if (i + 1 < chars.length && chars[i] == chars[i + 1]) {
                length = 0;
                for (int j = 1; i - j >= 0 && i + j + 1 < chars.length; j++) {
                    if (chars[i - j] != chars[i + j + 1]) {
                        break;
                    }
                    length++;
                }
                if (maxString.length() < length * 2 + 2) {
                    maxString = s.substring(i - length, i + length + 2);
                }
            }
        }
        return maxString;
    }


    /**
     * longestPalindrome优化，由一个中心和两个中心改为获取所有相同连续字符作为中心
     *
     * @param s
     * @return
     */
    public static String longestPalindrome2(String s) {
        String maxString = "";
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int sameLength = 0;
            // 将与当前字符相同的连续字符作为中心（偶数个和奇数个不影响）
            for (int j = 1; i + j < chars.length; j++) {
                if (chars[i + j] == chars[i]) {
                    sameLength++;
                } else {
                    break;
                }
            }
            int length = 0, i1 = sameLength + i;
            for (int j = 1; i - j >= 0 && i1 + j < chars.length; j++) {
                if (chars[i - j] != chars[i1 + j]) {
                    break;
                }
                length++;
            }
            if (maxString.length() < length * 2 + sameLength + 1) {
                maxString = s.substring(i - length, i + sameLength + length + 1);
            }

            i += sameLength;
        }
        return maxString;
    }

    //TODO
    public static String longestPalindrome3(String s) {
        return null;
    }
}
