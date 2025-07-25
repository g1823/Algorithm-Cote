package leetCode.difficult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 76. 最小覆盖子串
 */
public class MinWindow {

    public String minWindow(String s, String t) {
        Map<Integer, Integer> originalCharCount = new HashMap<>(26);
        for (char c : t.toCharArray()) {
            originalCharCount.put((int) c, originalCharCount.getOrDefault((int) c, 0) + 1);
        }
        Map<Integer, Integer> windowCharCount = new HashMap<>(26);
        return "";
    }
}
