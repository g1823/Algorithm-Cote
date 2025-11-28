package leetCode.moderately;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gj
 * @description: 647. 回文子串
 */
public class CountSubstrings {
    public static void main(String[] args) {
        String s = "aaa";
        System.out.println(new CountSubstrings().countSubstrings(s));
    }

    /**
     * 动态规划：
     * 如果知道以下标i为结尾的所有回文子串，那么对于i+1来说，只要 i+1的字符 = i的回文开头字符的前一个字符即可
     *
     */
    public int countSubstrings(String s) {
        if (s.length() == 0) {
            return 0;
        }
        List<Integer> dp = new ArrayList<>();
        dp.add(0);
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            ArrayList<Integer> thisList = new ArrayList<>();
            for (int j = 0; j < dp.size(); j++) {
                int index = dp.get(j);
                if (index - 1 < 0) {
                    continue;
                }
                if (s.charAt(index - 1) == s.charAt(i)) {
                    count++;
                    thisList.add(index - 1);
                }
            }
            // 上述无法判断当前元素=上一个元素所组成的回文情况，特殊处理
            if (s.charAt(i - 1) == s.charAt(i)) {
                count++;
                thisList.add(i - 1);
            }
            // 单独一个字符也算回文
            count++;
            thisList.add(i);
            dp = thisList;
        }
        return count;
    }

    //TODO
    public int countSubstrings2(String s) {
        int count = 0, n = s.length();
        if (n == 0) return 0;


        return count;
    }
}
