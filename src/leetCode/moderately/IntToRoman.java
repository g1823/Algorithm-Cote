package leetCode.moderately;

/**
 * @author: gj
 * @description: 12. 整数转罗马数字
 */
public class IntToRoman {

    public static void main(String[] args) {
        System.out.println(new IntToRoman().intToRoman(3749));
    }

    public static int[] nums = {1000, 500, 100, 50, 10, 5, 1};
    public static String[] romans = {"M", "D", "C", "L", "X", "V", "I"};

    public String intToRoman(int num) {
        int n = nums.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int t = nums[i];
            String s = String.valueOf(num);
            if('9' == s.charAt(0)){
                sb.append(romans[i-1]);
            }else if('4' == s.charAt(0)){

            }else{
                while (num >= t) {
                    num -= t;
                    sb.append(romans[i]);
                }
            }
        }
        return sb.toString();
    }
}
