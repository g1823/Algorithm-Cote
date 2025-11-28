package leetCode.simple;

import java.util.*;

/**
 * @author: gj
 * @description: 290. 单词规律
 */
public class WordPattern {

    /**
     * 错误(其实就是{@link IsAnagram} 242. 有效的字母异位词 的解法)
     * 这道题题目介绍有问题，只说明了需要看能否一一对应的映射，没有明确说明顺序也一致，也就是说：aabb 和 dog app dog app 这两个无法对应，因为顺序不一致。
     * 若不考虑顺序：
     * 1、记录pattern中每个字符出现次数，记录s中每个单词出现的次数
     * 2、对出现次数排序后比较，出现不一致就返回false
     */
    public boolean wordPattern(String pattern, String s) {
        Map<Character, Integer> pMap = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (pMap.containsKey(c)) {
                pMap.put(c, pMap.get(c) + 1);
            } else {
                pMap.put(c, 1);
            }
        }
        Map<String, Integer> sMap = new HashMap<>();
        String[] split = s.split(" ");
        for (int i = 0; i < split.length; i++) {
            String c = split[i];
            if (sMap.containsKey(c)) {
                sMap.put(c, sMap.get(c) + 1);
            } else {
                sMap.put(c, 1);
            }
        }
        if (pMap.size() != sMap.size()) {
            return false;
        }
        List<Integer> pList = new ArrayList<>();
        for (Character c : pMap.keySet()) {
            pList.add(pMap.get(c));
        }
        List<Integer> sList = new ArrayList<>();
        for (String c : sMap.keySet()) {
            sList.add(sMap.get(c));
        }
        pList.sort((o1, o2) -> o2 - o1);
        sList.sort((o1, o2) -> o2 - o1);
        for (int i = 0; i < pList.size(); i++) {
            if (!pList.get(i).equals(sList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean wordPattern2(String pattern, String s) {
        char[] chars = pattern.toCharArray();
        String[] split = s.split(" ");
        if (chars.length != split.length) {
            return false;
        }
        Map<Character, String> pToS = new HashMap<>();
        Map<String, Character> sToP = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            String s1 = split[i];
            if (pToS.containsKey(c) && !pToS.get(c).equals(s1)) {
                return false;
            }
            if (sToP.containsKey(s1) && !sToP.get(s1).equals(c)) {
                return false;
            }
            pToS.put(c, s1);
            sToP.put(s1, c);
        }
        return true;
    }
}
