package leetCode.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 242. 有效的字母异位词
 */
public class IsAnagram {

    /**
     * 解法：{@link WordPattern#wordPattern(java.lang.String, java.lang.String)}
     */
    public boolean isAnagram(String s, String t) {
        Map<String, Integer> sMap = new HashMap<>();
        Map<String, Integer> tMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            if (sMap.containsKey(c)) {
                sMap.put(c, sMap.get(c) + 1);
            } else {
                sMap.put(c, 1);
            }
        }
        for (int i = 0; i < t.length(); i++) {
            String c = t.substring(i, i + 1);
            if (tMap.containsKey(c)) {
                tMap.put(c, tMap.get(c) + 1);
            } else {
                tMap.put(c, 1);
            }
        }
        if (sMap.size() != tMap.size()) {
            return false;
        }
        for (String c : sMap.keySet()) {
            if (!sMap.get(c).equals(tMap.get(c))) {
                return false;
            }
        }
        return true;
    }
}
