package leetCode.moderately;

/**
 * @author: gj
 * @description: 48. 旋转图像
 */
public class Rotate {

    /**
     * 旋转图像
     * 思路：遍历矩阵左上角的元素，依次将左上角元素旋转至右上角对应位置，原右上角元素旋转至右下角对应位置，原右下角元素旋转至左下角对应位置，原左下角元素旋转至左上角对应位置
     * 现在需要了解的是四个元素的旋转转换关系：
     * 假设左上角元素下标为 i,j ，那么可以画图，作垂线，通过全等三角形，可以得到
     * 其旋转90度后右上角的对应坐标为 j,n-1-i
     * 然后再旋转90度，得到右下角的坐标为 n-1-i,n-1-j
     * 最后再旋转90度，得到左下角的坐标为 n-1-j,i
     * 然后避免元素被覆盖，则可以逆序赋值，即左上角临时存储，左上角赋值左下角，左下角赋值右下角，右下角赋值右上角，右上角赋值临时存储的元素
     */
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        // 遍历每个层
        for (int i = 0; i < n / 2; i++) {
            // 每层处理 n - 1 - 2*i 个元素
            for (int j = i; j < n - 1 - i; j++) {
                // 四个点依次交换
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

}
