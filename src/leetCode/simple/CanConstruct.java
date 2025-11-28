package leetCode.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 383. 赎金信
 */
public class CanConstruct {
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (map.containsKey(c)) {
                int count = map.get(c);
                if (count == 1) {
                    map.remove(c);
                } else {
                    map.put(c, count - 1);
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
