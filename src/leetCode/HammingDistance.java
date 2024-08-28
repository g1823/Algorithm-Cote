package leetCode;

/**
 * @author: gj
 * @description: 461 汉明距离
 */
public class HammingDistance {
    public static void main(String[] args) {
        HammingDistance hammingDistance = new HammingDistance();
        System.out.println(hammingDistance.hammingDistance(1, 4));
        System.out.println(hammingDistance.hammingDistance2(1, 4));
    }

    public int hammingDistance(int x, int y) {
        int count = 0;
        int xor = x ^ y;
        for (int i = 0; i < 32; i++) {
            if (((xor >> i) & 1) == 1) count++;
        }
        return count;
    }

    /**
     * f(x)=x & (x−1) ： f(x) 为 x 删去其二进制表示中最右侧的 1 的结果
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance2(int x, int y) {
        int s = x ^ y, ret = 0;
        while (s != 0) {
            s &= s - 1;
            ret++;
        }
        return ret;
    }

}
