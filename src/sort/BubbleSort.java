package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] data = {5, 0, 3, 20, 6, 2, 1, 4, 7, 8};
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        int n = data.length;
        if (n == 0 || n == 1) return data;
        for (int i = n; i > 0; i--) {
            boolean flag = true;
            for (int j = 1; j < i; j++) {
                if (data[j - 1] > data[j]) {
                    swap(data, j, j - 1);
                    flag = false;
                }
            }
            if (flag) break;
        }

        return data;
    }

    public static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
