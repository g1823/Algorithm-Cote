package basicAlgorithm.decrease_conquer;


import basicAlgorithm.divide_conquer.MergeSort;

/**
 * @author: gj
 * @date: 2024/5/28 16:09
 * @description: 二分查找
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] data = {8, 4, 5, 7, 1, 3, 6, 2};
        int[] result = MergeSort.execute(data, 0, data.length - 1);
        System.out.println(execute(result, 2));
    }

    public static int execute(int[] data, int key) {
        int leftIndex = 0, rightIndex = data.length - 1;
        int midIndex = -1;
        // 当左下标不再小于右下标，说明区间内已经没有元素，即数组内没有待查找元素
        while (leftIndex < rightIndex) {
            midIndex = (rightIndex - leftIndex) / 2 + leftIndex;
            // 找到就直接返回
            if (data[midIndex] == key) {
                return midIndex;
            //中间值小于待查找值，待查找值在数组右半区域
            } else if (data[midIndex] < key) {
                leftIndex = midIndex + 1;
            //中间值大于待查找值，待查找值在数组左半区域
            } else {
                rightIndex = midIndex - 1;
            }
        }
        return data[midIndex] == key ? midIndex : -1;
    }
}
