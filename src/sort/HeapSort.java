package sort;

import java.util.Arrays;

/**
 * @author: gj
 * @date: 2024/6/3 17:17
 * @description: 堆排序
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] data = {8, 4, 5, 7, 1, 3, 6, 2};
        execute(data);
        System.out.println(Arrays.toString(data));
    }

    public static void execute(int[] data) {
        int length = data.length;
        buildMaxHeap(data);
        for (int i = length - 1; i > 0; ) {
            swap(data, 0, i);
            adjustHeap(data, --i, 0);
        }
    }

    public static void buildMaxHeap(int data[]) {
        int n = data.length;
        // 数组初始为一个无序的完全二叉树，从最后一个非叶子节点开始调整
        for (int i = n / 2 - 1; i >= 0; i--) {
            adjustHeap(data, n - 1, i);
        }
    }

    public static void adjustHeap(int[] data, int length, int i) {
        //if (i == 0) return;
        int leftChildIndex = 2 * i + 1;
        int rightChildIndex = 2 * i + 2;
        int maxIndex = i;
        if (leftChildIndex <= length && data[leftChildIndex] > data[maxIndex]) {
            maxIndex = leftChildIndex;
        }
        if (rightChildIndex <= length && data[rightChildIndex] > data[maxIndex]) {
            maxIndex = rightChildIndex;
        }
        if (maxIndex != i) {
            swap(data, i, maxIndex);
            adjustHeap(data, length, maxIndex);
        }
    }

    public static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
