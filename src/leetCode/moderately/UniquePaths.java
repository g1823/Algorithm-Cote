package leetCode.moderately;

/**
 * @author: gj
 * @description: 62. 不同路径
 */
public class UniquePaths {
    public static void main(String[] args) {
        System.out.println(new UniquePaths().uniquePaths(3, 7));
    }

    public int uniquePaths(int m, int n) {
        int[] upRow = new int[n];
        int result = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                upRow[j] += result;
                result = upRow[j];
            }
            result = 0;
        }
        return upRow[n - 1];
    }
}
