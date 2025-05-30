package leetCode.moderately;


import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 3. 无重复字符的最长子串
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        String s = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(s));
    }

    /**
     * 思路：
     * 1. 遍历字符串，将字符串中的字符放入数组中，如果数组中存在该字符，则将数组中该字符之前的元素删除，
     * 同时将数组的长度减去该字符之前的元素个数，并更新最大长度。
     * 2. 如果数组中不存在该字符，则将该字符放入数组中，并更新最大长度。.
     * 分析：
     * 时间复杂度：O(n^2)
     * 外层循环：遍历整个字符串（n 次），每次处理一个字符。
     * 内层操作：对于每个字符，最坏情况下需要检查并复制当前无重复子串的所有字符（最多 n 次），因此内层操作是 O(n) 的。
     * 总复杂度：嵌套循环导致 O(n × n) = O(n²)。
     **/
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int max = 1, length = 1;
        char[] temp = new char[chars.length];
        temp[0] = chars[0];
        for (int i = 1; i < chars.length; i++) {
            char thisChar = chars[i];
            for (int j = 0; j < length; j++) {
                if (temp[j] == thisChar) {
                    length = length - j - 1;
                    System.arraycopy(temp, j + 1, temp, 0, length);
                    break;
                }
            }
            max = Math.max(max, length + 1);
            temp[length++] = thisChar;
        }
        return max;
    }

    /**
     * 滑动窗口:
     * 1. 创建一个哈希表，用于记录字符出现的最后位置。
     * 2. 创建一个变量 max 来记录最长子串的长度，初始化为 0。
     * 3. 创建一个变量 left，用于记录滑动窗口的左边界，初始化为 0。
     * 4. 遍历字符串，对于每个字符：
     * a. 如果字符已经出现过，更新滑动窗口的左边界，使其指向字符最后出现的位置的下一个位置。
     * b. 更新字符最后出现的位置。
     * c. 更新最长子串的长度，使其为当前滑动窗口的长度与最长子串的长度中的较大值。
     * <p>
     * 分析：
     * 使用一个 map 记录每个字符上一次出现的位置。
     * 遍历字符串，对于每个下标 k，假设下标为 k-1 时，窗口 [left, k-1] 是一个无重复子串。
     * 若当前字符 s[k] 已在 map 中出现过，说明窗口左边界可能需要右移。
     * 为了保持子串无重复，从 map 中查找字符 s[k] 上次出现的位置，将 left 更新为 max(left, map[s[k]] + 1)。
     * 这样可以保证窗口左右边界始终递增，不回退。
     */
    public static int lengthOfLongestSubstring1(String s) {
        if (s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>(16);
        char[] data = s.toCharArray();
        int max = 0, left = 0;
        for (int i = 0; i < data.length; i++) {
            char c = data[i];
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }
            map.put(c, i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }
}
