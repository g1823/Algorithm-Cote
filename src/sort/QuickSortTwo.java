package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2024/5/28 17:32
 * @description: 快排
 */
public class QuickSortTwo {
    public static void main(String[] args) {
        int[] data = {5, 4, 2, 7, 1, 3, 6, 8};
        System.out.println(Arrays.toString(execute(data, 0, data.length - 1)));
    }

    public static int[] execute(int[] data, int left, int right) {
        if (left >= right) {
            return null;
        }
        int result = quickSort(data, left, right);
        execute(data, left, result - 1);
        execute(data, result + 1, right);
        return data;
    }

    public static int quickSort(int[] data, int left, int right) {
        if (left >= right) {
            return -1;
        }
        int key = data[right], l = left, r = right;
        while (l < r) {
            while (l < r && data[l] < key) {
                l++;
            }
            if (l < r) {
                data[r--] = data[l];
            }
            while (l < r && data[r] > key) {
                r--;
            }
            if (l < r) {
                data[l++] = data[r];
            }
        }
        data[l]=key;
        return l;
    }

}
