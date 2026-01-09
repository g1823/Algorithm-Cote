package leetCode.simple;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 67. 二进制求和
 */
public class AddBinary {
    public static void main(String[] args) {
        String s1 = "11";
        String s2 = "1";
        System.out.println(new AddBinary().addBinary(s1, s2));
    }

    public String addBinary(String a, String b) {
        char[] chars1 = a.toCharArray();
        char[] chars2 = b.toCharArray();
        int c1Length = chars1.length;
        int c2Length = chars2.length;
        int n = Math.max(c1Length, c2Length);
        char[] result = new char[n];
        boolean flag = false;
        for (int i = 1; i <= n; i++) {
            char c1 = c1Length >= i ? chars1[c1Length - i] : '0';
            char c2 = c2Length >= i ? chars2[c2Length - i] : '0';
            int r = 0;
            r += c1 == '1' ? 1 : 0;
            r += c2 == '1' ? 1 : 0;
            r += flag ? 1 : 0;
            flag = r > 1;
            result[n - i] = (r % 2) == 0 ? '0' : '1';
        }
        StringBuilder r = new StringBuilder();
        if(flag){
            r.append('1');
        }
        for (char c : result) {
            r.append(c);
        }
        return r.toString();
    }
}
