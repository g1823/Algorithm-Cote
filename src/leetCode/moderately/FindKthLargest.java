package leetCode.moderately;

/**
 * @author: gj
 * @description: 215. 数组中的第K个最大元素
 */
public class FindKthLargest {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 1, 5, 6, 4};
        System.out.println(new FindKthLargest().findKthLargest2(nums, 2));
    }

    // 基于堆排序
    public int findKthLargest(int[] nums, int k) {
        int length = nums.length;
        if (length < k) return Integer.MIN_VALUE;
        buildMaxHeap(nums);
        for (int i = length - 1, j = 0; i > 0 && j < k; j++) {
            swap(nums, 0, i);
            adjustHeap(nums, --i, 0);
        }
        return nums[length - k];
    }

    public void buildMaxHeap(int[] data) {
        int n = data.length;
        // 数组初始为一个无序的完全二叉树，从最后一个非叶子节点开始调整
        for (int i = n / 2 - 1; i >= 0; i--) {
            adjustHeap(data, n - 1, i);
        }
    }

    public void adjustHeap(int[] data, int length, int i) {
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

    public void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


    // 基于快排
    public int findKthLargest2(int[] nums, int k) {
        return quickSort(nums, -1, nums.length - 1, k - 1);
    }

    public int quickSort(int[] nums, int left, int right, int k) {
        if (left == k) return nums[left];
        int t = nums[left + 1], l = left + 1, r = right;
        while (l < r) {
            while (l < r && nums[r] < t) r--;
            if (l < r) nums[l++] = nums[r];
            while (l < r && nums[l] > t) l++;
            if (l < r) nums[r--] = nums[l];

        }
        nums[l] = t;
        if (l == k) return nums[l];
        else if (l < k) return quickSort(nums, l, right, k);
        else return quickSort(nums, left, l - 1, k);
    }


}
