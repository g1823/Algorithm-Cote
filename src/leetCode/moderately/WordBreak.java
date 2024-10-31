package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 139. 单词拆分
 */
public class WordBreak {
    public static void main(String[] args) {
        WordBreak wordBreak = new WordBreak();
//        String s = "leetcode";
//        List<String> wordDict = new ArrayList<>(Arrays.asList("leet", "code"));
        String s = "aaaaaaa";
        List<String> wordDict = new ArrayList<>(Arrays.asList("aaaa", "aaa"));
        System.out.println(wordBreak.wordBreak(s, wordDict));
    }

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

}
