package leetCode.simple;

/**
 * @author: gj
 * @description: 125. 验证回文串、9. 回文数
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
        } else if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
            return c;
        } else {
            return (char) '-';
        }
    }

    /**
     * 9. 回文数
     * 首先，可以跟验证回文串一样，将数字转为字符串，然后判断是否回文。
     * 但是要求不转换为字符串，因此需要转换思路：
     * 1、首先想到，将整个数字进行反转，如果是回文，那么反转前后两个数字是相等的。但是反转整个数字可能会出现数字溢出的问题：
     * - 在大多数编程语言中，int类型通常是32位有符号整数
     * - 范围是：-2,147,483,648 到 2,147,483,647
     * - 当反转一个接近最大值的数字时，反转后的数字可能超出这个范围
     * - 例如：反转前：2,147,483,647 ，反转后：7,463,847,412，数字溢出了
     * 2.可以考虑只反转一半，将x的右侧一半数字反转，与x的左侧一半数字进行对比，相等则说明是回文。
     * - 操作流程：
     * -    第一位：对x%10得到最低位数，然后y+=x%10;
     * -    第二位：x/=10 去掉最低位，然后y*=10,y+=x%10。将y原来数字左移一位，再加上次低位
     * -    后续：不断重复，直到y>=x
     * - 如何知道反转数字的位数已经达到原始数字位数的一半：
     * -    x有效位数为奇数的情况下：当y>=x时，y实际比x多一位，比如12321，反转后x会变为12，y变为123.
     * - 然后是否为回文实际上跟中间位3无关，因此直接对比x==y/10;
     * -    x有效位数为偶数的情况下：当y>=x时，x和y就是一样的，结束。
     *
     * @param x 待判断的数
     * @return 是否回文
     */
    public boolean isPalindrome(int x) {
        // 1、负数一定不是回文,最高位不可能为0，因此若个位为0，那么必须x=0才能是回文
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int y = 0;
        while (x > y) {
            y = y * 10 + x % 10;
            x /= 10;
        }
        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == y || x == y / 10;
    }
}
