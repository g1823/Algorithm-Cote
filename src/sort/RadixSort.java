package sort;

import java.util.Arrays;

/**
 * @Package sort
 * @Date 2024/9/4 22:22
 * @Author gaojie
 * @description: 基数排序
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] data = {5, 0, 3, 20, 6, 2, 1, 4, 7, 8};
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        int maxRadix = getMaxRadix(data);
        for (int i = 1; i <= maxRadix; i++) {
            int[] counter = new int[10];
            int exp = 1;
            for (int t = i; t > 1; t--) {
                exp *= 10;
            }
            for (int j = 0; j < data.length; j++) {
                int index = getDigit(data[j], exp);
                counter[index] += 1;
            }
            for (int k = 1; k < counter.length; k++) {
                counter[k] += counter[k - 1];
            }
            int[] temp = new int[data.length];
            for (int l =  data.length -1 ; l >= 0; l--) {
                int index = getDigit(data[l], exp);
                counter[index] -= 1;
                temp[counter[index]] = data[l];
            }
            swapArray(temp, data);
        }
        return data;
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

    public static void swapArray(int[] sourceArray, int[] targetArray) {
        for (int i = 0; i < sourceArray.length; i++) {
            targetArray[i] = sourceArray[i];
        }
    }
}
