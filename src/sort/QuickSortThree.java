package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2024/5/28 17:32
 * @description: 快排
 */
public class QuickSortThree {
    public static void main(String[] args) {
        int[] data = {8, 4, 5, 7, 1, 3, 6, 5, 2};
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
        int key = data[left], l = left, r = right;
        while (true) {
            while (l < r && data[l] < key) {
                l++;
            }
            while (l < r && data[r] > key) {
                r--;
            }
            if (l >= r) {
                return l;
            }
            if (data[l] == key && data[r] == key) {
                r--;
                while (l < r && data[r] > key) {
                    r--;
                }
            }
            swap(data, l, r);
        }
    }

    public static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }
}
