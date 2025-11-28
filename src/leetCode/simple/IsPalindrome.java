package leetCode.simple;

/**
 * @author: gj
 * @description: 125. 验证回文串
 */
public class IsPalindrome {
    public static void main(String[] args) {
        String s = ".,";
        boolean palindrome = new IsPalindrome().isPalindrome(s);
        System.out.println(palindrome);
    }

    public boolean isPalindrome(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        char[] chars = s.toCharArray();
        while (left < right) {
            char leftChar = toLowerCase(chars[left]);
            while (leftChar == '-' && left < right) {
                left++;
                leftChar = toLowerCase(chars[left]);
            }
            char rightChar = toLowerCase(chars[right]);
            while (rightChar == '-' && left < right) {
                right--;
                rightChar = toLowerCase(chars[right]);
            }
            if (leftChar != rightChar) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public char toLowerCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32);
        } else if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')){
            return c;
        } else {
            return (char) '-';
        }
    }
}
