package leetCode.moderately;

/**
 * @author: gj TODO
 * @description: 240. 搜索二维矩阵 II
 */
public class SearchMatrix {
    public static void main(String[] args) {

        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                System.out.print(i + "," + j + "  ");
            }
            System.out.println();
        }


        int[][] matrix = new int[][]{
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}};
        int target = 5;
        System.out.println(new SearchMatrix().searchMatrix(matrix, target));
    }

    public boolean searchMatrix(int[][] matrix, int target) {

        return true;
    }

    public boolean test(int[] leftBorder, int[] rightBorder, int[][] matrix, int target) {
        int leftX = leftBorder[0], leftY = leftBorder[1], rightX = rightBorder[0], rightY = rightBorder[1];
        int n = Math.min(rightX - leftX, rightY - leftY);
        for (int i = 1; i < n; i++) {
            int x1 = leftX + i, y1 = leftY + i, x2 = rightX - i, y2 = rightY - i;
            if (x1 < x2 && y1 < y2) {

            }
        }
        return true;
    }
}
