package leetCode.simple;

/**
 * @description: 3120. 统计特殊字母的数量 I
 */
public class NumberOfSpecialChars {
    public int numberOfSpecialChars(String word) {
        boolean[] up = new boolean[26];
        boolean[] low = new boolean[26];
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c >= 'a' && c <= 'z') {
                low[c - 'a'] = true;
            } else {
                up[c - 'A'] = true;
            }
        }
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (up[i] && low[i]) {
                count++;
            }
        }
        return count;
    }
}
