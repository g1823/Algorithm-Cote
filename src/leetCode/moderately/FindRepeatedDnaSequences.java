package leetCode.moderately;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gj
 * @description: 187. 重复的DNA序列
 */
public class FindRepeatedDnaSequences {

    /**
     * 直接用map记录每一个子串出现的次数
     */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length() - 9; i++) {
            String sub = s.substring(i, i + 10);
            map.put(sub, map.getOrDefault(sub, 0) + 1);
            if (map.get(sub) == 2) {
                res.add(sub);
            }
        }
        return res;
    }

    /**
     * 优化，使用位运算
     * 因为只有4钟字符，因此可以使用一个两个bit进行映射，十个字符共20位
     * 一个int 32位，大于20位，因此可以用一个int来存储一个子串
     */
    public List<String> findRepeatedDnaSequences2(String s) {
        List<String> res = new ArrayList<>();
        Map<Character, Integer> t = new HashMap<>();
        t.put('A', 0);
        t.put('C', 1);
        t.put('G', 2);
        t.put('T', 3);
        if (s.length() < 10) {
            return res;
        }
        int x = 0;
        for (int i = 0; i < 10 - 1; ++i) {
            x = (x << 2) | t.get(s.charAt(i));
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= s.length() - 10; ++i) {
            // 移动一位，丢弃最左边的，添加最右边的
            x = ((x << 2) | t.get(s.charAt(i + 10 - 1))) & ((1 << (10 * 2)) - 1);
            map.put(x, map.getOrDefault(x, 0) + 1);
            if (map.get(x) == 2) {
                res.add(s.substring(i, i + 10));
            }
        }
        return res;
    }
}
