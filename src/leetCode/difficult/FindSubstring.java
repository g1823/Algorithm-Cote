package leetCode.difficult;

import java.util.*;

/**
 * @author: gj
 * @description: 30. 串联所有单词的子串
 */
public class FindSubstring {

    public static void main(String[] args) {
        String s = "wordgoodgoodgoodbestword";
        String[] words = {"word", "good", "best", "good"};
        String s1 = "barfoothefoobarman";
        String[] words1 = {"foo", "bar"};
        String s2 = "a";
        String[] words2 = {"a", "a"};
        String s3 = "ababababab";
        String[] words3 = {"ababa","babab"};
        String s4 = "aaaaaaaaaaaaaa";
        String[] words4 = {"aa","aa"};
        String s5 = "dbaaacbcbbabdcabdacabadcbcadcadbbacbcdadaaadcabbadbbdbdadddcdcdbcbcaacda";
        String[] words5 = {"aad","bcd","ada"};
        System.out.println(new FindSubstring().findSubstring2(s5, words5));
    }

    /**
     * 错误
     * 思路：
     * 1. 使用一个 map 统计 words 中每个单词的出现次数。
     * 2. 定义一个 window，初始复制 map（表示当前还需要匹配的单词数量）。
     * 3. 使用两个指针 left 和 right 来维护窗口，窗口每次扩展一个单词长度 m。
     * 4. 如果窗口遇到的单词在 window 中，则减少计数，直到 window 为空时，说明匹配成功，记录下标。
     * 5. 如果遇到不在 window 中的单词，尝试恢复窗口或重置窗口。
     * 存在的问题：
     * - 没有处理不同偏移量（只能检测从 0 开始的情况，会漏解）。
     * - window 初始化为 map 的副本，导致恢复逻辑复杂。
     * - 匹配成功后的恢复逻辑写死（只恢复左端第一个单词），不够通用。
     * - 遇到不匹配的单词时，用 putAll(map) 重置窗口，效率低。
     */
    public List<Integer> findSubstring(String s, String[] words) {
        // 1. 统计 words 中每个单词出现的次数
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        int n = s.length();
        // 每个单词长度
        int m = words[0].length();
        int left = 0, right = 0;

        // 2. 窗口初始化为 map 的拷贝（表示还需要匹配的单词）
        // ⚠️ 问题：这里直接复制 map，后面窗口匹配逻辑会变复杂
        Map<String, Integer> window = new HashMap<>(map);

        List<Integer> res = new ArrayList<>();

        // 3. 主循环：不断扩展右指针，每次扩展一个单词
        // ⚠️ 这里没有考虑偏移量，只能保证从 0 开始的子串被检测到
        while (right < n && left + m * words.length <= n) {
            String sub = s.substring(right, right + m);

            // 3.1 如果当前单词在 window 中
            if (window.containsKey(sub)) {
                Integer integer = window.get(sub);

                // 3.1.1 如果该单词只剩 1 次需要匹配，移除
                if (integer == 1) {
                    window.remove(sub);

                    // 3.1.1.1 如果 window 空了，说明窗口正好匹配 words
                    if (window.isEmpty()) {
                        res.add(left);

                        // ⚠️ 恢复逻辑：强制把 left 指向的单词补回 window，并 left += m
                        //   问题：写死了恢复逻辑，不适合所有情况
                        String sub2 = s.substring(left, left + m);
                        window.put(sub2, 1);
                        left += m;
                    }
                } else {
                    // 3.1.2 如果该单词还需要多次，则计数减一
                    window.put(sub, integer - 1);
                }
                // 扩展右指针
                right += m;
            } else {
                // 3.2 当前单词不在 window 中

                if (map.containsKey(sub)) {
                    // 3.2.1 如果这个单词在原始 map 中，但在 window 中已用完
                    // 说明窗口内出现了多余的 sub，需要移动 left 直到窗口合法
                    while (left < right) {
                        String sub2 = s.substring(left, left + m);
                        if (sub2.equals(sub)) {
                            // 找到多余的单词，跳过它，重新定位 right
                            left += m;
                            right = left + m;
                            break;
                        } else {
                            // 没找到的话，把 sub2 重新加入 window（恢复匹配次数）
                            window.put(sub2, window.getOrDefault(sub2, 0) + 1);
                        }
                        left += m;
                    }
                } else {
                    // 3.2.2 当前单词既不在 window 中，也不在 map 中
                    // 说明出现了完全无关的单词，直接重置窗口
                    // ⚠️ 问题：这里用 putAll(map) 效率很低，每次都 O(k)
                    window.putAll(map);
                    left = right + m;
                    right = left;
                }
            }
        }
        return res;
    }

    /**
     * 串联所有单词的子串
     * 思路：
     * 1. 因为每个单词长度相同，记为 m，总窗口长度为 resLength = words.length * m；
     * 2. 使用滑动窗口 + HashMap 来统计窗口内的单词频次；
     * 3. 每次只需从 [0, m-1] 作为起点，因为不同余数的起点会覆盖所有可能情况；
     * 4. 窗口内维护一个 window Map，统计单词出现次数；
     *    同时用一个 windowSuccessSet 来维护哪些单词频次完全匹配；
     * 5. 如果窗口完全匹配（windowSuccessSet.size() == map.size()），记录答案；
     * 6. 当窗口右侧遇到非法单词时，直接整体跳到 right + m 重新初始化窗口；
     * 7. 当窗口内单词合法时，就正常移除左侧单词，加入右侧单词，继续滑动。
     * 复杂度：
     * - 时间复杂度：O(n * m)，每个起点最多扫描一次；
     * - 空间复杂度：O(k)，k 为不同单词数。
     */
    public List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int n = s.length(), m = words[0].length(), resLength = words.length * m;
        if(n < resLength){
            return res;
        }
        // 构建目标词频统计 map
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        // 维护窗口的统计
        Map<String, Integer> window = new HashMap<>();
        Set<String> windowSuccessSet = new HashSet<>();
        // 只需要考虑从0、1、...、m-1开头的，因为m+1开头的会在以1开头的情况中进行处理
        for (int i = 0; i < m && i + resLength <= n; i++) {
            String sub = s.substring(i, i + resLength);
            // 初始化窗口统计
            initWindow(map, window, windowSuccessSet, sub, m);
            if (windowSuccessSet.size() == map.size()) {
                res.add(i);
            }
            int left = i, right = i + resLength;
            // 还有向后扩展的可能，即左侧加长度加单词长度小于总长度
            while (right + m <= n) {
                // 去掉左端单词
                String subLeft = s.substring(left, left + m);
                if (map.containsKey(subLeft)) {
                    window.put(subLeft, window.getOrDefault(subLeft, 0) - 1);
                    if (window.get(subLeft).equals(map.get(subLeft))) {
                        windowSuccessSet.add(subLeft);
                    } else {
                        windowSuccessSet.remove(subLeft);
                    }
                }
                // 扩展右端单词
                String subRight = s.substring(right, right + m);
                if (map.containsKey(subRight)) {
                    window.put(subRight, window.getOrDefault(subRight, 0) + 1);
                    if (window.get(subRight).equals(map.get(subRight))) {
                        windowSuccessSet.add(subRight);
                    }else{
                        windowSuccessSet.remove(subRight);
                    }
                    if (windowSuccessSet.size() == map.size()) {
                        res.add(left + m);
                    }
                    left += m;
                    right += m;
                } else {
                    // 窗口包含非法单词，直接跳到 right+m 重新初始化窗口
                    int newLeft = right + m;
                    if (newLeft + resLength <= n) {
                        left = newLeft;
                        right = newLeft + resLength;
                        initWindow(map, window, windowSuccessSet, s.substring(left, right), m);
                        if (windowSuccessSet.size() == map.size()) {
                            res.add(left);
                        }
                    } else {
                        // 已经无法再形成完整窗口，退出
                        break;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 初始化窗口统计
     * 将字符串 windowStr 按单词长度 width 分割，填充 window 和 windowSuccessSet
     */
    private void initWindow(Map<String, Integer> map, Map<String, Integer> window, Set<String> windowSuccessSet, String windowStr, int width) {
        window.clear();
        windowSuccessSet.clear();
        for (int j = 0; j < windowStr.length(); j += width) {
            String sub2 = windowStr.substring(j, j + width);
            if (map.containsKey(sub2)) {
                window.put(sub2, window.getOrDefault(sub2, 0) + 1);
                if (window.get(sub2).equals(map.get(sub2))) {
                    windowSuccessSet.add(sub2);
                } else {
                    windowSuccessSet.remove(sub2);
                }
            }
        }
    }

}
