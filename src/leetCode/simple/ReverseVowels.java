package leetCode.simple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: gj
 * @description: 345. 反转字符串中的元音字母
 */
public class ReverseVowels {

    /**
     * 双指针：
     * - 跟快排快慢指针类似，这里使用双指针，一个从左向右，一个从右向左，找到元音字母，交换位置
     */
    public String reverseVowels(String s) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            while (left < right && !vowels.contains(chars[left])) {
                left++;
            }
            while (left < right && !vowels.contains(chars[right])) {
                right--;
            }
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

}
