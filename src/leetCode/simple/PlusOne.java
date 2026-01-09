package leetCode.simple;

/**
 * @author: gj
 * @description: 66. 加一
 */
public class PlusOne {
    public static void main(String[] args) {
        int[] digits = {9,9,9};
        System.out.println(new PlusOne().plusOne(digits));
    }
    public int[] plusOne(int[] digits) {
        boolean flag = true;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (flag) {
                if (digits[i] == 9) {
                    digits[i] = 0;
                    flag = true;
                }else{
                    digits[i]++;
                    return digits;
                }
            } else {
                flag = false;
                break;
            }
        }
        if (flag) {
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            return newDigits;
        }
        return digits;
    }
}
