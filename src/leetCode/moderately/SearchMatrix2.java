package leetCode.moderately;

/**
 * @author: gj
 * @description: 240. 搜索二维矩阵 II
 */
public class SearchMatrix2 {
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

    /**
     * 错误
     */
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

    /**
     * 思路：
     * 根据矩阵的性质，从左到右递增，从上到下递增。
     * 如果正常遍历，会遇到一个问题，比如
     * - 坐标 matrix(i,j) < target，那么即可以向右走，又可以向下走，无法直接过滤当前行或当前列
     * - 坐标 matrix(i,j) > target，那么即可以向上走，又可以向左走，无法直接过滤当前行或当前列
     * 改变思路，从右上角开始遍历：
     * - 如果当前元素小于目标元素，因为当前行所有未被过滤的元素都小于当前元素，而当前元素小于目标元素，则当前行所有元素均小于目标元素。可以过滤掉当前行，则只能取下一行。
     * - 如果当前元素大于目标元素，因为从右上角往左下角遍历，而当前元素上方的元素已经被直接过滤，当前元素下方的元素都大于当前元素，因此只可以过滤掉当前列，只能能取上一列。
     * 这样，一次过滤一行或一列，直到找到目标元素或者遍历完所有元素。
     * 换个说法，从右上角 (0, n-1) 开始搜索
     * 每次比较当前元素和目标值 target：
     * - 若 matrix[i][j] == target，返回 true
     * - 若 matrix[i][j] < target，由于当前列下面的元素可能更大，所以向下移动 i++
     * - 若 matrix[i][j] > target，由于当前行左边的元素可能更小，所以向左移动 j--
     * 每次移动都可以排除一行或一列，时间复杂度 O(m + n)
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        // 从右上角开始
        int i = 0, j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] < target) {
                // 当前元素小于目标元素，则只能取下一行
                i++;
            } else {
                // 当前元素大于目标元素，则只能取上一列
                j--;
            }
        }
        return false;
    }
}
