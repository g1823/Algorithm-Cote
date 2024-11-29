package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 438. 找到字符串中所有字母异位词
 */
public class FindAnagrams {
    public static void main(String[] args) {
        //System.out.println(new FindAnagrams().findAnagrams("cbaebabacd", "abc"));
        //System.out.println(new FindAnagrams().findAnagrams("abab", "ab"));
        System.out.println(new FindAnagrams().findAnagrams("abacbabc", "abc"));
    }

    /**
     * 模仿KMP思想，进来让主串s不回退
     * 匹配到s串的第i个字符，从s串的第k个位置到i-1个位置均已经匹配成功（符合下述规则）：
     * s[i]是否为p串中的字符：
     * ---是：设s[i]为字符j,s串中已经匹配的字符中j是否已经超过p串中字符j的数量
     * ------是：说明字符j超出，那么从k位置向后遍历，找到第一个字符j,设这个位置为l,然后从l+1的位置从新开始
     * ------否：未超出，继续向后遍历，并记录让p串中字符j数量减一
     * ---否：s[i]不是p串中的字符，则直接从i+1的位置重新开始。
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        Map<Character, Integer> pMap = new HashMap<>();
        int pLength = p.length();
        for (int i = 0; i < pLength; i++) {
            char c = p.charAt(i);
            if (pMap.containsKey(c)) {
                pMap.put(c, pMap.get(c) + 1);
            } else {
                pMap.put(c, 1);
            }
        }
        int currentLength = 0;
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> currentMap = new HashMap<>(pMap);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (pMap.containsKey(c)) {
                if (currentMap.containsKey(c)) {
                    Integer count = currentMap.get(c);
                    if (count > 1) {
                        currentMap.put(c, count - 1);
                    } else {
                        currentMap.remove(c);
                    }
                    currentLength++;
                } else {
                    i = i - currentLength;
                    while (c != s.charAt(i)) i++;
                    currentLength = 0;
                    currentMap.clear();
                    currentMap.putAll(pMap);
                }
            } else {
                currentLength = 0;
                currentMap.clear();
                currentMap.putAll(pMap);
            }
            if (currentMap.isEmpty()) {
                int back = i - currentLength + 1;
                result.add(back);
                currentMap.putAll(pMap);
                currentLength = 0;
                for (int i2 = back + 1; i2 <= i; i2++) {
                    char c1 = s.charAt(i2);
                    Integer count = currentMap.get(c1);
                    if (count > 1) {
                        currentMap.put(c1, count - 1);
                    } else {
                        currentMap.remove(c1);
                    }
                    currentLength++;
                }
            }
        }
        return result;
    }
}
