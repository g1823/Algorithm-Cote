package leetCode.difficult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gj
 * @description: 68. 文本左右对齐
 */
public class FullJustify {


    public static void main(String[] args) {
        String[] words = {"What", "must", "be", "acknowledgment", "shall", "be"};
        int maxWidth = 16;
        List<String> result = new FullJustify().fullJustify(words, maxWidth);
        System.out.println(result);
    }

    /**
     * 68. 文本左右对齐
     * <p>
     * 解题思路：
     * - 我们要把 words 按照 maxWidth 的宽度分行，分行规则是尽量多放单词，
     * 但保证这一行的长度（单词总长度 + 至少的空格）不超过 maxWidth。
     * - 对每一行：
     * 1. 如果是最后一行，或者只有一个单词：左对齐 → 单词之间放一个空格，右边补齐空格。
     * 2. 否则：完全对齐 → 空格均匀分配到单词间，若不能整除，则前面的间隔多一个空格。
     * - 关键在于：
     * - 如何分行（用 while 统计当前能放多少单词）。
     * - 如何分配空格（整除 + 余数）。
     * <p>
     * 时间复杂度：O(n)，n 为单词总字符数。
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int n = words.length;
        int start = 0;

        while (start < n) {
            int end = start;
            int sumLen = 0;
            while (end < n && sumLen + words[end].length() + (end - start) <= maxWidth) {
                sumLen += words[end].length();
                end++;
            }

            StringBuilder sb = new StringBuilder();
            int numWords = end - start;
            int numSpaces = maxWidth - sumLen;

            // 最后一行或只有一个单词：左对齐
            if (end == n || numWords == 1) {
                for (int i = start; i < end; i++) {
                    sb.append(words[i]);
                    if (i < end - 1) {
                        sb.append(' ');
                    }
                }
                sb.append(blanks(maxWidth - sb.length()));
            } else {
                // 完全对齐
                int spaces = numSpaces / (numWords - 1);
                int extra = numSpaces % (numWords - 1);
                for (int i = start; i < end; i++) {
                    sb.append(words[i]);
                    if (i < end - 1) {
                        // 基础空格
                        sb.append(blanks(spaces));
                        // 前 extra 个间隔多一个空格
                        if (extra > 0) {
                            sb.append(' ');
                            extra--;
                        }
                    }
                }
            }
            result.add(sb.toString());
            start = end;
        }
        return result;
    }

    private static String blanks(int count) {
        if (count <= 0) {
            return "";
        }
        char[] arr = new char[count];
        Arrays.fill(arr, ' ');
        return new String(arr);
    }

}
