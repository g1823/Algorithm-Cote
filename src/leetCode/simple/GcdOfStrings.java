package leetCode.simple;

/**
 * @author: gj
 * @description: 1071. 字符串的最大公因子
 */
public class GcdOfStrings {
    public static void main(String[] args) {
        System.out.println(new GcdOfStrings().gcdOfStrings("ABCABC", "ABC"));
    }

    /**
     * 直接暴力枚举：
     * 首先，结果一定是str2的某个前缀串，就从str2的每个前缀串开始判断能否匹配str1和str2即可
     */
    public String gcdOfStrings(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        if (m < n) {
            return gcdOfStrings(str2, str1);
        }
        for (int i = n; i >= 0; i--) {
            String prefix = str2.substring(0, i);
            if (prefix.length() == 0) {
                break;
            }
            if (m % prefix.length() != 0) {
                continue;
            }
            if (n % prefix.length() != 0) {
                continue;
            }
            boolean flag = false;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < m; j += prefix.length()) {
                sb.append(prefix);
                if (sb.length() == n) {
                    flag = str2.equals(sb.toString());
                    if (!flag) {
                        break;
                    }
                }
            }
            if (!flag) {
                continue;
            }
            while (sb.length() < m) {
                sb.append(prefix);
            }
            if (sb.toString().equals(str1)) {
                return prefix;
            }
        }
        return "";
    }

    /**
     * - 1071. 字符串的最大公因子
     * - 关键点在于：假设str1和str2有公因子字符串X，那么str1 + str2 = str2 + str1
     * - 因为：str1 = k1 * X, str2 = k2 * X , 则： str1 + str2 = str2 + str1 = (k1 + k2) * X
     * - 然后：若 str1 + str2 = str2 + str1, 则说明 str2是str1的前缀，那么 str1 = str2 + l，
     * - 带入上面式子; str2 + l = l + str2。出现了辗转相除法
     * - ⭐ 关键点：假设X是str1和str2的最大公因子字符串，那么X也是l的最大公因子，且随着辗转相除的过程，这个X是不变的，一直是后续任何一个子串的最大公因子
     * - 那么当进行到X + Y = Y + X，且X=Y时，此时的X就是最大公因子字符串
     * - X不可能为空串，因为再进行到X = Y 时就终止了，不会再进行 新的 l = X - Y = ""了。
     * - ===========================
     * - 一、问题抽象
     * - ===========================
     * - 题目中的“字符串的最大公因子”，可以类比整数中的最大公约数（GCD）：
     * -
     * - - 若存在一个字符串 X，使得：
     * -      str1 = k1 个 X 拼接
     * -      str2 = k2 个 X 拼接
     * -   则称 X 为 str1 和 str2 的公因子字符串
     * -
     * - - 在所有公因子字符串中，长度最大的那个即为答案
     * -
     * - ===========================
     * - 二、从暴力到本质
     * - ===========================
     * - 直观暴力思路：
     * - - 枚举某个字符串的前缀
     * - - 判断该前缀能否整除（重复拼接）str1 和 str2
     * -
     * - 但进一步观察可以发现：
     * - - 如果 str1 和 str2 存在公因子字符串 X
     * - - 那么它们一定具有相同的“周期结构”
     * -
     * - ===========================
     * - 三、关键性质（核心不变量）
     * - ===========================
     * - 若 str1 和 str2 存在公因子字符串 X，则必然满足：
     * -
     * -      str1 + str2 == str2 + str1
     * -
     * - 证明（必要性）：
     * - - 设 str1 = k1 * X，str2 = k2 * X
     * - - 则：
     * -      str1 + str2 = (k1 + k2) * X
     * -      str2 + str1 = (k2 + k1) * X
     * - - 二者必然相等
     * -
     * - 同时，这个条件也是【充分条件】：
     * - - 如果 str1 + str2 != str2 + str1
     * - - 说明二者不具备统一的周期结构
     * - - 不可能存在任何公因子字符串
     * -
     * - 因此：
     * - >>> 判断 str1 + str2 == str2 + str1，是“是否存在答案”的前置条件
     * -
     * - ===========================
     * - 四、字符串版辗转相除法（思想来源）
     * - ===========================
     * - 对整数：
     * -      gcd(a, b) = gcd(b, a % b)
     * -
     * - 对字符串（假设 str1.length >= str2.length）：
     * - - 若 str1 + str2 == str2 + str1
     * - - 则 str2 一定是 str1 的前缀
     * - - 可以写成：
     * -      str1 = str2 + L
     * -
     * - 将其代入：
     * -      str1 + str2 = str2 + str1
     * - =>   str2 + L + str2 = str2 + str2 + L
     * - =>   L + str2 = str2 + L
     * -
     * - 这说明：
     * - - gcd(str1, str2) = gcd(str2, L)
     * - - L 相当于字符串版的 “a % b”
     * -
     * - 不断递归下去：
     * - - 要么某一步 L 为空串（说明两串相等，直接终止）
     * - - 要么最终收敛到两个相同的字符串 X
     * -
     * - 此时：
     * -      X 即为最大公因子字符串
     * -
     * - ===========================
     * - 五、从字符串递归到长度递归
     * - ===========================
     * - 上述字符串版辗转相除法，关注的是“周期结构”
     * -
     * - 一旦确认：
     * -      str1 + str2 == str2 + str1
     * -
     * - 那么：
     * - - str1 和 str2 一定由同一个最小周期重复构成
     * - - 最大公因子字符串的长度
     * -      == gcd(str1.length, str2.length)
     * -
     * - 因此：
     * - - 不需要真的做字符串递归
     * - - 只需要对“长度”做辗转相除法
     * -
     * - ===========================
     * - 六、最终算法流程
     * - ===========================
     * - 1. 若 str1 + str2 != str2 + str1，直接返回 ""
     * - 2. 计算 gcd(len(str1), len(str2))
     * - 3. 返回 str1 的前 gcd 长度子串
     * -
     * - ===========================
     * - 七、复杂度分析
     * - ===========================
     * - - 时间复杂度：
     * -      O(n + m)，拼接判断 + gcd
     * - - 空间复杂度：
     * -      O(1)（不考虑拼接产生的新字符串）
     */
    public String gcdOfStrings2(String str1, String str2) {
        if (!str1.concat(str2).equals(str2.concat(str1))) {
            return "";
        }
        return str1.substring(0, gcd(str1.length(), str2.length()));

    }

    public int gcd(int a, int b) {
        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
}
