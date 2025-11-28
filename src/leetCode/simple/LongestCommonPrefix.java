package leetCode.simple;

/**
 * @author: gj
 * @description: 14. 最长公共前缀
 */
public class LongestCommonPrefix {

    /**
     * 以第一个字符串为基准，一个一个元素对比
     */
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || c != strs[j].charAt(i)) {
                    return sb.toString();
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
