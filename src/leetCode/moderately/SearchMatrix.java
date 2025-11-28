package leetCode.moderately;

/**
 * @author: gj
 * @description: 74. 搜索二维矩阵
 */
public class SearchMatrix {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}};
        int target = 3;
        System.out.println(new SearchMatrix().searchMatrix(matrix, target));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        // 直接将二维数组拉伸开，转换为一维数组操作
        int low = 0, high = m * n - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            int x = matrix[mid / n][mid % n];
            if (x < target) {
                low = mid + 1;
            } else if (x > target) {
                high = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
