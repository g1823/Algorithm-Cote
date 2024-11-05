package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 139. 单词拆分
 */
public class WordBreak {
    public static void main(String[] args) {
        WordBreak wordBreak = new WordBreak();
        String s = "leetcode";
        List<String> wordDict = new ArrayList<>(Arrays.asList("leet", "code"));
//        String s = "aaaaaaa";
//        List<String> wordDict = new ArrayList<>(Arrays.asList("aaaa", "aaa"));
        System.out.println(wordBreak.wordBreak2(s, wordDict));
    }

    /**
     * 回溯法，超时
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        return dfs(s, 0, wordSet);
    }

    public boolean dfs(String s, int index, Set<String> wordDict) {
        if (index >= s.length()) {
            return true;
        }
        for (int length = 1; length + index <= s.length(); length++) {
            String substring = s.substring(index, index + length);
            boolean result = false;
            if (wordDict.contains(substring)) {
                result = dfs(s, index + length, wordDict);
            }
            if (result) return true;
            if (length >= 20) return false;
        }
        return false;
    }

    /**
     * 动态规划
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int length = s.length();
        boolean[] dp = new boolean[length + 1];
        dp[0] = true;
        for (int i = 1; i < length + 1; i++) {
            int j = i > 20 ? i - 20 : 0;//因为每个单词最长20
            for (; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }


}
