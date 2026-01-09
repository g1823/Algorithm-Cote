package leetCode.simple;

/**
 * @author: gj
 * @description: 605. 种花问题
 */
public class CanPlaceFlowers {
    public static void main(String[] args) {
        int[] flowerbed = new int[]{1, 0, 0, 0, 1};
        System.out.println(new CanPlaceFlowers().canPlaceFlowers(flowerbed, 2));
    }

    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            boolean last = i == 0 || flowerbed[i - 1] == 0;
            boolean next = i == flowerbed.length - 1 || flowerbed[i + 1] == 0;
            if (flowerbed[i] == 0 && last && next) {
                flowerbed[i] = 1;
                count++;
            }
        }
        return count >= n;
    }
}
