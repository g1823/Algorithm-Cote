package leetCode.moderately;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: gj
 * @description: 1456. 定长子串中元音的最大数目
 */
public class MaxVowels {
    /**
     * 滑动窗口：
     * 1. 创建滑动窗口，窗口大小为k
     * 2. 每向右滑动一格，判断窗口内新增的字符是否是元音，是则计数器加1
     * 3. 每向右滑动一格，判断窗口内移出的字符是否是元音，是则计数器减1
     */
    public int maxVowels(String s, int k) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        int max = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < k; i++) {
            char c = chars[i];
            if (vowels.contains(c)) {
                max++;
            }
        }
        int count = max;
        for (int i = k; i < chars.length; i++) {
            char last = chars[i - k];
            char current = chars[i];
            if (vowels.contains(last)) {
                count--;
            }
            if (vowels.contains(current)) {
                count++;
            }
            max = Math.max(max, count);
        }
        return max;
    }
}
