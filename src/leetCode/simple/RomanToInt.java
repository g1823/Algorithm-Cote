package leetCode.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 13. 罗马数字转整数
 */
public class RomanToInt {
    public static void main(String[] args) {
        String s = "III";
        System.out.println(new RomanToInt().romanToInt(s));
    }

    private static final Map<Character, Integer> MAP = new HashMap<>();

    static {
        MAP.put('I', 1);
        MAP.put('V', 5);
        MAP.put('X', 10);
        MAP.put('L', 50);
        MAP.put('C', 100);
        MAP.put('D', 500);
        MAP.put('M', 1000);
    }

    public int romanToInt(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (i < n - 1 && MAP.get(chars[i]) < MAP.get(chars[i + 1])) {
                res += MAP.get(chars[i + 1]) - MAP.get(chars[i]);
                i++;
            } else {
                res += MAP.get(chars[i]);
            }
        }
        return res;
    }
}
