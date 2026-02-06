package leetCode.moderately;

/**
 * @author: gj
 * @description: 1318. 或运算的最小翻转次数
 */
public class MinFlips {
    /**
     * 直接模拟，从最低位到最高位依次判断32次
     * 设 a[i]表示a的第i位，b[i]表示b的第i位，c[i]表示c的第i位
     * 若c[i]=1，那么a[i]或b[i]有一个为1，则不需要翻转，否则需要翻转任意一个，次数+1
     * 若c[i]=0，那么a[i]和b[i]都需要为0，否则，若a[i]=1,则需要翻转a[i]，次数+1,若b[i]=1,则需要翻转b[i]，次数+1
     */
    public int minFlips(int a, int b, int c) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            // c的第i位为1
            if ((1 & (c >> i)) == 1) {
                // a和b的第i位任意一位为1，则不需要翻转
                if ((1 & (a >> i)) == 1 || (1 & (b >> i)) == 1) {

                } else {
                    res++;
                }
            }
            // c的第i位为0
            else {
                if ((1 & (a >> i)) == 1) {
                    res++;
                }
                if ((1 & (b >> i)) == 1) {
                    res++;
                }
            }
        }
        return res;
    }
}
