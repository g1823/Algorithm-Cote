package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 希尔排序
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] data = {5, 0, 3, 20, 6, 2, 1, 4, 7, 8};
        int i = 1;
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        int gap = data.length;
        while (gap > 1) {
            gap /= 2;
            insertSort(data, gap);
        }
        return data;
    }

    public static void insertSort(int[] data, int gap) {
        for (int thisIndex = gap; thisIndex < data.length; thisIndex += gap) {
            int temp = data[thisIndex];
            int j = thisIndex - gap;
            while (j >= 0 && temp < data[j]) {
                data[j + gap] = data[j];
                j -= gap;
            }
            data[j + gap] = temp;
        }
    }
}
