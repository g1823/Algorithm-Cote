package leetCode.moderately;

/**
 * @author: gj
 * @description: 12. 整数转罗马数字
 */
public class IntToRoman {

    public static void main(String[] args) {
        System.out.println(new IntToRoman().intToRoman(3749));
    }

    /**
     * 思路：
     * 直接建立数字合罗马数字的映射关系，题目说明num <= 3999
     * 然后从大到小依次匹配就可以了。
     * 映射关系：
     * 个位数：
     * - 数字：1、2、3、4、5、6、7、8、9
     * - 罗马：I、II、III、IV、V、VI、VII、VIII、IX
     * 十位数：
     * - 数字：10、20、30、 40、50、60、70、 80、90
     * - 罗马：X、 XX、XXX、XL、L、 LX、LXX、LXXX、XC
     * 百位数：
     * - 数字：100、200、300、400、500、600、700、800、900
     * - 罗马：C、  CC、 CCC、CD、 D、  DC、 DCC、DCCC、CM
     * 千位数：
     * - 数字：1000、2000、3000
     * - 罗马：M、MM、MMM
     */
    public String intToRoman(int num) {
        int[] nums = {3000, 2000, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        String[] romans = {"MMM", "MM", "M", "CM", "DCCC", "DCC", "DC", "D", "CD", "CCC", "CC", "C", "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};
        int n = nums.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int t = nums[i];
            if (num < t) {
                continue;
            }
            sb.append(romans[i]);
            num -= t;
        }
        return sb.toString();
    }

    /**
     * 思路：模拟
     * 对intToRoman的优化：
     * 实际可以发现，对于如下数字：
     * -个位：1、2、3、6、7、8
     * -十位：10、20、30、60、70、80
     * -百位：100、200、300、600、700、800
     * -千位：1000、2000、3000
     * 这些数字实际上都是有规律的，都是基数-1或+1，当数字为4、5、9、10时，会变一下，只需要把这几个特殊的处理即可。
     * 比如：
     * - 8实际上就是特殊字符 V(5) + 1(I) + 1(I) + 1(I) = VIII，只需要特殊处理5，即先除去5取余，得到3，然后3并非特殊字符，依次添加I即可。
     * - 对于9而言，因为标识方法不再是VIIII，而是IX，因此特殊处理，当当前数字是9，直接取IX即可。
     * - 其他位类似。
     */
    public String intToRoman2(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuffer roman = new StringBuffer();
        for (int i = 0; i < values.length; ++i) {
            int value = values[i];
            String symbol = symbols[i];
            while (num >= value) {
                num -= value;
                roman.append(symbol);
            }
            if (num == 0) {
                break;
            }
        }
        return roman.toString();
    }
}
