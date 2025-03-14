package leetCode.moderately;

/**
 * @author: gj TODO
 * @description: 240. 搜索二维矩阵 II
 */
public class SearchMatrix {
    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print("(" + i + "," + j + ") ");
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
        int[][] matrix2 = new int[][]{{-1, 3}};
        int target2 = -1;
        int[][] matrix3 = new int[][]{{1, 3, 5, 7, 9}, {2, 4, 6, 8, 10}, {11, 13, 15, 17, 19}, {12, 14, 16, 18, 20}, {21, 22, 23, 24, 25}};
        int target3 = 13;
        int[][] matrix4 = new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        int target4 = 15;
        //System.out.println(new SearchMatrix().searchMatrix(matrix4, target4));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        return test(new int[]{0, 0}, new int[]{matrix[0].length - 1, matrix.length - 1}, matrix, target);
    }

    public boolean test(int[] leftBorder, int[] rightBorder, int[][] matrix, int target) {
        int leftX = leftBorder[0], leftY = leftBorder[1], rightX = rightBorder[0], rightY = rightBorder[1];
        if (leftX == rightX) {
            int l = leftY, r = rightY;
            while (l <= r) {
                int mid = l + (r - l + 1) / 2;
                int d = matrix[mid][leftX];
                if (d > target) {
                    r = mid - 1;
                } else if (d < target) {
                    l = mid + 1;
                } else {
                    return true;
                }
            }
        }
        if (leftY == rightY) {
            int l = leftX, r = rightX;
            while (l <= r) {
                int mid = l + (r - l + 1) / 2;
                int d = matrix[leftY][mid];
                if (d > target) {
                    r = mid - 1;
                } else if (d < target) {
                    l = mid + 1;
                } else {
                    return true;
                }
            }
        }
        int n = Math.min(rightX - leftX, rightY - leftY);
        if (n <= 0) return false;
        SearchResult searchResult = searchTarget(matrix, leftX, leftY, n, target);
        if (searchResult.isFind) return true;
        int[] leftIndex = getLeftIndex(leftBorder, rightBorder);
        int[][] ints = divideSquare(leftBorder, rightBorder, searchResult);
        // 先将以leftBorder为左上角，长度为n的正方形被切割部分，再查剩余部分
        return test(ints[0], ints[1], matrix, target) || test(ints[2], ints[3], matrix, target) || test(leftIndex, rightBorder, matrix, target);
    }

    public int[] getLeftIndex(int[] leftBorder, int[] rightBorder) {
        int xLength = rightBorder[0] - leftBorder[0];
        int yLength = rightBorder[1] - leftBorder[1];
        int[] left = new int[2];
        if (xLength < yLength) {
            left[0] = leftBorder[0] + xLength;
            left[1] = leftBorder[1];
        } else if (xLength > yLength) {
            left[0] = leftBorder[0];
            left[1] = leftBorder[1] + yLength;
        } else {
            return rightBorder;
        }
        return left;
    }

    public int[][] divideSquare(int[] leftBorder, int[] rightBorder, SearchResult searchResult) {
        int[] left1 = new int[2], right1 = new int[2], left2 = new int[2], right2 = new int[2];
        left1[0] = searchResult.x;
        left1[1] = leftBorder[1];
        right1[0] = rightBorder[0];
        right1[1] = searchResult.y;
        left2[0] = leftBorder[0];
        left2[1] = searchResult.y;
        right2[0] = searchResult.x;
        right2[1] = rightBorder[1];
        return new int[][]{left1, right1, left2, right2};
    }

    public SearchResult searchTarget(int[][] matrix, int x, int y, int n, int target) {
        int l = 0, r = n;
        while (l <= r && r <= n) {
            int mid = l + (r - l + 1) / 2;
            int d = matrix[y + mid][x + mid];
            if (d < target) {
                l = mid + 1;
            } else if (d > target) {
                r = mid - 1;
            } else {
                SearchResult searchResult = new SearchResult();
                searchResult.isFind = true;
                return searchResult;
            }
        }
        SearchResult searchResult = new SearchResult();
        searchResult.isFind = false;
        searchResult.x = x + l;
        searchResult.y = y + l;
        return searchResult;
    }

    class SearchResult {
        boolean isFind;
        int x;
        int y;
    }
}
