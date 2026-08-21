package leetCode.simple;

/**
 * @description: 3069. 将元素分配到两个数组中 I
 */
public class ResultArray {
    public int[] resultArray(int[] nums) {
        int n = nums.length;
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        int lIdx = 0, rIdx = 0;
        arr1[lIdx] = nums[0];
        arr2[lIdx] = nums[1];
        for (int i = 2; i < n; i++) {
            if (arr1[lIdx] > arr2[rIdx]) {
                arr1[++lIdx] = nums[i];
            } else {
                arr2[++rIdx] = nums[i];
            }
        }
        int[] result = new int[n];
        for (int i = 0; i <= lIdx; i++) {
            result[i] = arr1[i];
        }
        for (int i = 0; i <= rIdx; i++) {
            result[i + lIdx + 1] = arr2[i];
        }

        return result;
    }
}
