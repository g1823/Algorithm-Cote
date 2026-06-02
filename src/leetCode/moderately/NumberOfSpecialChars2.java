package leetCode.moderately;

import java.util.Arrays;

/**
 * @description: 3121. 统计特殊字母的数量 II
 */
public class NumberOfSpecialChars2 {
    public static void main(String[] args) {
        String word = "AbBCab";
        System.out.println(new NumberOfSpecialChars2().numberOfSpecialChars(word));
    }

    public int numberOfSpecialChars(String word) {
        int[] up = new int[26];
        int[] low = new int[26];
        Arrays.fill(up, -1);
        Arrays.fill(low, -1);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c >= 'a' && c <= 'z') {
                // 注意，题目要求每个小写的c都需要在大写c之前，也就意味着要记录最后一个小写c的下标
                low[c - 'a'] = i;
            } else {
                // 记录第一个大写c的下标
                if(up[c - 'A'] == -1){
                    up[c - 'A'] = i;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (up[i] != -1 && low[i] != -1 && up[i] > low[i]) {
                count++;
            }
        }
        return count;
    }
}
