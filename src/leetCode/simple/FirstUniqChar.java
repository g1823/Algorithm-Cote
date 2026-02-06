package leetCode.simple;

/**
 * @author: gj
 * @description: 387. 字符串中的第一个唯一字符
 */
public class FirstUniqChar {
    public static void main(String[] args) {
        System.out.println(new FirstUniqChar().firstUniqChar("aadadaad"));
    }

    public int firstUniqChar(String s) {
        int[] exists = new int[26];
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (exists[chars[i] - 'a'] == 0) {
                // 取 i + 1，可以避免0位置索引，一位0位置字符并未出现过
                exists[chars[i] - 'a'] = i + 1;
            } else {
                exists[chars[i] - 'a'] = -1;
            }
        }
        int first = Integer.MAX_VALUE;
        for (int i = 0; i < exists.length; i++) {
            if (exists[i] > 0 && exists[i] < first) {
                first = exists[i];
            }
        }
        return first == Integer.MAX_VALUE ? -1 : first - 1;
    }
}
