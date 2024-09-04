package sort;

/**
 * @Package sort
 * @Date 2024/9/4 22:22
 * @Author gaojie
 * @description: 基数排序
 */
public class RadixSort {
    public static void main(String[] args) {

    }

    public static int[] execute(int[] data) {
        int maxRadix = getMaxRadix(data);

        return null;
    }

    public static int[] radixSort(int[] data, int maxRadix) {
        int[] counter = new int[maxRadix];
        for (int i = 1; i <= maxRadix; i++) {

        }
        return null;
    }

    public static int getDigit(int data, int exp) {
        return (data / exp) % 10;
    }

    public static int getMaxRadix(int[] data) {
        int maxRadix = 1;
        int base = 10;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > base) {
                base *= 10;
                maxRadix++;
            }
        }
        return maxRadix;
    }
}
