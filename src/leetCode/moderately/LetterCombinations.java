package leetCode.moderately;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: gj
 * @description: 17. 电话号码的字母组合
 */
public class LetterCombinations {
    public static void main(String[] args) {
        List<String> strings = letterCombinations2("");
        System.out.println(strings);
    }

    /**
     * 给定一个仅包含数字2-9的字符串，返回所有它能表示的字母组合。
     * 模拟电话按键数字与字母的映射关系，使用迭代方式逐步构造所有组合。
     * 思路：
     * 1. 依次遍历输入的每个数字；
     * 2. 将当前数字对应的所有字母，依次与前一轮的组合拼接，得到新的组合列表；
     * 3. 最终返回所有完整组合。
     *
     * @param digits 电话号码数字字符串
     * @return 所有可能的字母组合
     */
    public static List<String> letterCombinations(String digits) {
        List<String> lastComb = new ArrayList<>();
        char[] data = digits.toCharArray();
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        for (char aChar : data) {
            List<String> thisComb = new ArrayList<>();
            char[] chars = mapping[aChar - '0'].toCharArray();
            if (lastComb.isEmpty()) {
                for (char c : chars) {
                    lastComb.add("" + c);
                }
                continue;
            }
            for (char c : chars) {
                for (String s : lastComb) {
                    thisComb.add(s + c);
                }
            }
            lastComb = thisComb;
        }
        return lastComb;
    }


    /**
     * 回溯法解决
     *
     * @param digits 电话号码数字字符串
     * @return 所有可能的字母组合
     */
    public static List<String> letterCombinations2(String digits) {
        if (digits == null || digits.length() == 0) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        char[] data = digits.toCharArray();
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        dfs(data, mapping, 0, new StringBuilder(), result);
        return result;
    }

    private static void dfs(char[] data, String[] mapping, int index, StringBuilder sb, List<String> result) {
        if (index == data.length) {
            result.add(sb.toString());
            return;
        }

        String letters = mapping[data[index] - '0'];
        for (int i = 0; i < letters.length(); i++) {
            sb.append(letters.charAt(i));
            dfs(data, mapping, index + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
