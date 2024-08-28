package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2024/5/29 15:40
 * @description: 插入排序
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] data = {8, 4, 5, 7, 1, 3, 6, 5, 2};
        System.out.println(Arrays.toString(execute(data)));
    }

    public static int[] execute(int[] data) {
        for (int thisIndex = 1; thisIndex < data.length; thisIndex++) {
            int temp = data[thisIndex], i = thisIndex;
            while (i >= 0) {
                if (i != 0 && temp < data[i - 1]) {
                    data[i] = data[i-- - 1];
                } else {
                    data[i] = temp;
                    break;
                }
            }
        }
        return data;
    }
}
