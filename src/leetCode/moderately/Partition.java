package leetCode.moderately;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gj
 * @description: 131. 分割回文串
 */
public class Partition {

    public static void main(String[] args) {
        String s = "efe";
        System.out.println(new Partition().partition(s));
    }

    /**
     * 方法：分割回文串
     * 思路：
     * 1. 使用回溯（backtracking）生成所有可能的子串分割。
     * 2. 每次尝试从当前位置 index 开始的子串，如果是回文，则加入路径。
     * 3. 到达字符串末尾时，当前路径即为一个有效分割，加入结果集。
     * 4. 使用 isPalindrome 方法判断子串是否回文，每次判断会重复计算，可以进一步优化为 DP。
     */
    public List<List<String>> partition(String s) {
        // 存放最终结果集
        List<List<String>> res = new ArrayList<>();
        // dp[i][j] 用于记录子串 s[i..j] 是否为回文，当前版本未使用，可用于优化
        // 从索引 0 开始回溯，生成所有回文分割
        backtrack(s, new ArrayList<>(), 0, res);
        // 返回所有分割结果
        return res;
    }

    /**
     * 回溯方法：生成所有回文分割
     */
    public void backtrack(String s, List<String> path, int index, List<List<String>> res) {
        // 到达字符串末尾，当前路径为一个有效分割
        if (index >= s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 从 index 开始尝试每个子串
        int end = index + 1;
        // 遍历从 index 到字符串末尾的所有子串
        while (end <= s.length()) {
            // 如果子串 s[index..end) 是回文
            if (isPalindrome(s, index, end)) {
                // 记录当前 path 的大小，用于回溯清理
                int size = path.size();
                // 将回文子串加入路径
                path.add(s.substring(index, end));
                // 递归处理剩余子串
                backtrack(s, path, end, res);
                // 回溯：移除上一步加入的子串
                path.subList(size, path.size()).clear();
            }
            // 尝试下一个子串
            end++;
        }
    }

    /**
     * 判断子串是否回文
     */
    public boolean isPalindrome(String str, int start, int end) {
        // 获取子串
        String substring = str.substring(start, end);
        // 双指针判断首尾字符是否相等
        for (int s = 0, e = substring.length() - 1; s < e; s++, e--) {
            if (substring.charAt(s) != substring.charAt(e)) {
                return false;
            }
        }
        // 是回文
        return true;
    }

    /**
     * 方法：分割回文串（DP 优化版）
     * 思路：
     * 1. 原始方法是标准回溯 + 每次用 isPalindrome 判断子串回文性。
     * 问题：每次判断回文都要遍历子串，存在大量重复计算，效率低。
     * 2. 优化思路：
     * - 预处理所有子串回文性，使用二维 dp 表记录 dp[i][j] 表示 s[i..j] 是否是回文。
     * - 填充 dp 表：如果首尾字符相等，且长度 <=2 或内部子串是回文，则该子串为回文。
     * - 回溯时直接查询 dp 表，避免重复遍历子串。
     * 3. 回溯逻辑保持不变：从当前位置 index 开始，每次尝试把 s[index..end] 作为回文子串加入路径。
     * - 到达字符串末尾时，路径即为一个有效分割，加入结果集。
     * - 回溯时移除最后加入的子串，尝试下一个可能回文。
     */
    public List<List<String>> partition2(String s) {
        // 存放最终结果集
        List<List<String>> res = new ArrayList<>();
        // 字符串长度
        int n = s.length();
        // dp[i][j] 表示子串 s[i..j] 是否为回文
        boolean[][] dp = new boolean[n][n];
        // 预处理所有回文子串
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                // 如果首尾字符相等且长度 <= 2 或内部子串是回文，则 s[j..i] 是回文
                dp[j][i] = (s.charAt(i) == s.charAt(j)) && (i - j < 2 || dp[j + 1][i - 1]);
            }
        }
        // 从索引 0 开始回溯，生成所有回文分割
        backtrack2(s, new ArrayList<>(), 0, res, dp);
        // 返回所有分割结果
        return res;
    }

    /**
     * 回溯方法：生成所有回文分割（使用 dp 查询回文）
     */
    public void backtrack2(String s, List<String> path, int index, List<List<String>> res, boolean[][] dp) {
        // 到达字符串末尾，当前路径为一个有效分割
        if (index >= s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 尝试从 index 开始的每个子串
        for (int end = index; end < s.length(); end++) {
            // 如果子串 s[index..end] 是回文（直接查询 dp 表）
            if (dp[index][end]) {
                // 将回文子串加入路径
                path.add(s.substring(index, end + 1));
                // 递归处理剩余子串
                backtrack2(s, path, end + 1, res, dp);
                // 回溯：移除最后加入的子串
                path.remove(path.size() - 1);
            }
        }
    }


    /**
     * 方法：分割回文串（记忆化回溯）
     * 思路：
     * 1. 使用回溯生成所有分割，但对子串回文性或剩余子串的分割结果使用缓存。
     * 2. 定义 Map<Integer, List<List<String>>> memo：
     * - key：起始索引 index
     * - value：从 index 到末尾的所有回文分割结果
     * 3. 回溯时：
     * - 如果 memo 中已有 index 的结果，直接返回缓存，避免重复计算。
     * - 否则，从 index 向右尝试每个子串，判断是否回文。
     * - 是回文则递归处理剩余子串，将结果与当前子串组合。
     * - 缓存 index 对应的分割结果。
     * 4. 优化点：
     * - 减少重复递归计算，提高性能。
     * - 对于有大量重复子串的情况效果明显。
     */
    public List<List<String>> partitionMemo(String s) {
        // 存放最终结果
        List<List<String>> res = new ArrayList<>();
        // 缓存从 index 开始的所有分割结果
        Map<Integer, List<List<String>>> memo = new HashMap<>();
        // 从索引 0 开始回溯
        res = backtrackMemo3(s, 0, memo);
        // 返回最终结果
        return res;
    }

    /**
     * 回溯方法（带记忆化缓存）
     */
    private List<List<String>> backtrackMemo3(String s, int index, Map<Integer, List<List<String>>> memo) {
        // 如果缓存中已有结果，直接返回
        if (memo.containsKey(index)) {
            return memo.get(index);
        }
        // 存放从 index 开始的所有分割结果
        List<List<String>> res = new ArrayList<>();
        // 到达字符串末尾，返回空路径作为基准
        if (index == s.length()) {
            res.add(new ArrayList<>());
            return res;
        }
        // 尝试从 index 开始的每个子串
        for (int end = index; end < s.length(); end++) {
            // 判断子串 s[index..end] 是否回文
            if (isPalindrome3(s, index, end + 1)) {
                // 递归获取剩余子串的所有分割结果
                List<List<String>> sublists = backtrackMemo3(s, end + 1, memo);
                // 将当前回文子串与后续分割结果组合
                for (List<String> sublist : sublists) {
                    // 新建列表存放组合结果
                    List<String> newList = new ArrayList<>();
                    // 加入当前回文子串
                    newList.add(s.substring(index, end + 1));
                    // 加入后续分割
                    newList.addAll(sublist);
                    // 加入结果集
                    res.add(newList);
                }
            }
        }
        // 缓存 index 对应的分割结果
        memo.put(index, res);
        // 返回从 index 开始的所有分割
        return res;
    }

    /**
     * 判断子串是否回文
     */
    private boolean isPalindrome3(String str, int start, int end) {
        // 获取子串
        String substring = str.substring(start, end);
        // 双指针判断首尾字符是否相等
        for (int s = 0, e = substring.length() - 1; s < e; s++, e--) {
            if (substring.charAt(s) != substring.charAt(e)) {
                return false;
            }
        }
        // 是回文
        return true;
    }
}
