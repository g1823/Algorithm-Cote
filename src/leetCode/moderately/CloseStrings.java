package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 1657. 确定两个字符串是否接近
 */
public class CloseStrings {

    /**
     * hash哈希，这里因为只有小写字母，因此使用数组代替hash
     * 根据题目说明：
     * - 1、任意两个字符可换位置
     * - 2、两种不同字符可以互相转换
     * 那么可以做如下操作：
     * - 按照字符出现次数将字符升序排序（通过1换位置）
     * - 然后依次转换字符（通过2转换）
     * 那么可以知道：
     * 1、如果两个字符串长度不一致，不可能相等
     * 2、如果两个字符串中出现的字符不一致，那么字符串1无论如何也转换不出来自己不存在的字符
     * 3、如果两个字符串中字符出现的次数不一致，那么就无法转换的完全相等
     */
    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }
        int[] word1Count = new int[26];
        int[] word2Count = new int[26];
        for (int i = 0; i < word1.length(); i++) {
            word1Count[word1.charAt(i) - 'a']++;
            word2Count[word2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (word1Count[i] == 0 && word2Count[i] != 0 || word1Count[i] != 0 && word2Count[i] == 0) {
                return false;
            }
        }
        Arrays.sort(word1Count);
        Arrays.sort(word2Count);
        return Arrays.equals(word1Count, word2Count);
    }
}
