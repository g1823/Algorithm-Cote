package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 49. 字母异位词分组
 */
public class GroupAnagrams {

    /**
     * 思路：将每个字符串排序，然后作为key，将相同key的加入同一个list中
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>(strs.length);
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            if (!map.containsKey(key)) {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(key, list);
            } else {
                map.get(key).add(str);
            }
        }
        map.forEach((key, value) -> result.add(value));
        return result;
    }

    /**
     * 为了避免排序的时间复杂度，可以转换思路：
     * 每组字母异位词里面每一个词的字符个数都相同，所以可以统计每个词中每个字符出现的次数，次数相同的词就属于一组
     */
    public static List<List<String>> groupAnagrams2(String[] strs) {
        Map<String, List<String>> map = new HashMap<>(strs.length);
        for (String str : strs) {
            int[] counts = new int[26];
            for (char c : str.toCharArray()) {
                counts[c - 'a']++;
            }
            String key = Arrays.toString(counts);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }
}
