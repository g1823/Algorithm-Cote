package leetCode.difficult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: gj
 * @description: 76. 最小覆盖子串
 */
public class MinWindow {

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String result = new MinWindow().minWindow(s, t);
    }

    /**
     * 思路：滑动窗口
     * 通过滑动窗口，记录窗口内的字符信息，当满足条件时，窗口右侧不动，开始缩短窗口左侧，直至得到满足条件的最小值，并记录
     * 具体步骤：
     * 1、初始化
     * - 记录t串中每个字符出现的次数  originalCharCount
     * - 创建一个滑动窗口，并记录窗口中每个字符出现的次数  windowCharCount
     * - 创建一个set，记录窗口内已经满足出现次数的字符 validChar  ，当其size和originalCharCount的size一样，说明已经满足条件
     * 2、滑动窗口，记录窗口内满足条件的字符
     * - 每次向右移动窗口边界，新增一个字符，对于新字符进行判断
     * -- 若新字符是t中字符：
     * -- -- 则对窗口中该字符的出现次数进行+1，并判断窗口内该字符出现次数是否已经 > t串中该字符次数
     * -- -- -- 若大于，则加入validChar，然后判断是否已经满足t串要求，满足后收缩窗口左侧边界，直到再次收缩就不满足，然后记录
     * -- -- -- 若小于等于，则不加入validChar
     * -- 新字符不是t中字符：直接跳过，窗口继续向右扩张
     */
    public String minWindow(String s, String t) {
        // 记录t中字符出现的次数
        Map<Character, Integer> originalCharCount = new HashMap<>(26);
        for (char c : t.toCharArray()) {
            originalCharCount.put(c, originalCharCount.getOrDefault(c, 0) + 1);
        }
        // 记录窗口中字符出现的次数
        Map<Character, Integer> windowCharCount = new HashMap<>(26);
        int left = 0, right = 0;
        // 记录窗口中出现次数满足t串要求的字符
        Set<Character> validChar = new HashSet<>(26);
        String result = "";
        while (right < s.length()) {
            char c = s.charAt(right);
            if (originalCharCount.containsKey(c)) {
                int count = windowCharCount.getOrDefault(c, 0);
                int targetCount = originalCharCount.get(c);
                windowCharCount.put(c, count + 1);
                if (targetCount <= count + 1) {
                    if (!validChar.contains(c)) {
                        validChar.add(c);
                    }
                    // 当前窗口已经满足t串要求，进行收缩，以获取最小串
                    if (validChar.size() == originalCharCount.size()) {
                        while (left <= right && validChar.size() == originalCharCount.size()) {
                            char leftChar = s.charAt(left);
                            // 不是t串中的字符，直接收缩
                            if (!originalCharCount.containsKey(leftChar)) {
                                left++;
                                continue;
                            }
                            // 是t中的字符，判断收缩后是否还能满足t串要求
                            int leftCount = windowCharCount.getOrDefault(leftChar, 0);
                            int leftTargetCount = originalCharCount.get(leftChar);
                            // 当前窗口中该字符的个数大于t串同字符的数量，收缩窗口
                            if (leftCount > leftTargetCount) {
                                windowCharCount.put(leftChar, leftCount - 1);
                                left++;
                            } else {
                                // 当前窗口中该字符的个数等于t串同字符的数量，不可再收缩
                                result = result.length() == 0 ?
                                        s.substring(left, right + 1) :
                                        result.length() > right - left + 1 ? s.substring(left, right + 1) : result;
                                break;
                            }
                        }
                    }
                }
            }
            right++;
        }
        return result;
    }


    /**
     * 对于minWindow的优化，依旧是滑动窗口
     * 优化点对比原始实现：
     * 1. 使用 validCount 整型变量替代 Set<Character> validChar，提高效率并避免遗漏删除的情况；
     * 2. 字符满足条件的判断逻辑简化为 window[c] == target[c]，提升可读性；
     * 3. 子串更新逻辑从 while 循环中提取，避免多次重复判断；
     * 4. 移除了 HashMap 初始化容量为 26 的设定，更通用，避免浪费；
     * 5. 整体逻辑结构更清晰，将扩张窗口和收缩窗口逻辑分离。
     */
    public String minWindow2(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        // 统计 t 中各字符出现次数
        Map<Character, Integer> target = new HashMap<>();
        for (char c : t.toCharArray()) {
            target.put(c, target.getOrDefault(c, 0) + 1);
        }

        // 用于统计当前窗口中的字符出现次数
        Map<Character, Integer> window = new HashMap<>();
        // 窗口边界
        int left = 0, right = 0;
        // 当前有多少种字符满足要求
        int validCount = 0;
        // 当前最短子串长度
        int minLen = Integer.MAX_VALUE;
        // 记录最短子串的起始位置
        int start = 0;

        // 扩展右边界
        while (right < s.length()) {
            char c = s.charAt(right);
            // 若 c 是 t 中的字符，加入窗口计数
            if (target.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                // 如果数量刚好等于目标要求，说明该字符满足条件
                if (window.get(c).intValue() == target.get(c).intValue()) {
                    validCount++;
                }
            }

            // 当所有字符都满足要求时，开始尝试缩小窗口
            while (validCount == target.size()) {
                // 更新最小子串记录
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }

                // 收缩左边界
                char d = s.charAt(left);
                if (target.containsKey(d)) {
                    // 如果当前字符是关键字符，并且将被移除后不满足条件，更新 validCount
                    if (window.get(d).intValue() == target.get(d).intValue()) {
                        validCount--;
                    }
                    window.put(d, window.get(d) - 1);
                }
                left++;
            }

            // 扩展右边界
            right++;
        }

        // 返回结果
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
