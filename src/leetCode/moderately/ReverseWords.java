package leetCode.moderately;

/**
 * @author: gj
 * @description: 151. 反转字符串中的单词
 */
public class ReverseWords {

    public static void main(String[] args) {
        String s = "the sky is blue";

        System.out.println(new ReverseWords().reverseWords(s));
    }

    /**
     * 倒叙遍历，遇到一个非' '字符，就一直遍历直到找到下一个' '字符，也就是找到了一个完整单词，然后加入新字符串中
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != ' ') {
                int j = i - 1;
                while (j >= 0 && chars[j] != ' ') {
                    j--;
                }
                sb.append(chars, j + 1, i - j).append(" ");
                i = j;
            }
        }
        return sb.charAt(sb.length() - 1) == ' ' ? sb.substring(0, sb.length() - 1) : sb.toString();
    }
}
