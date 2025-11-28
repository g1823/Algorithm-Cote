package leetCode.simple;

/**
 * @author: gj
 * @description: 58. 最后一个单词的长度
 */
public class LengthOfLastWord {

    /**
     * 关键点，因为求最后一个单词长度，直接倒着找，找到第一个非空格字符，然后作为最后一个单词的最后一个字符开始遍历，直到遇到下一个空格字符
     */
    public int lengthOfLastWord(String s) {
        char black = ' ';
        char[] chars = s.toCharArray();
        int length = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != black) {
                length++;
            } else {
                if (length > 0) {
                    break;
                }
            }
        }
        return length;
    }
}
