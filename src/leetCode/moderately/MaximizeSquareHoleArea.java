package leetCode.moderately;

import java.util.Arrays;

/**
 * @author: gj
 * @description: 2943. 最大化网格图中正方形空洞的面积
 */
public class MaximizeSquareHoleArea {
    public static void main(String[] args) {
        int n = 2;
        int m = 3;
        int[] hBars = {2, 3};
        int[] vBars = {2, 4};
        System.out.println(new MaximizeSquareHoleArea().maximizeSquareHoleArea(n, m, hBars, vBars));
    }

    /**
     * 思路：
     * 最开始，发现去除某根线会影响其他答案，比如使得某个原来能组成正方形的情况变成了长方形。
     * 然后思考暴力枚举 ，列举每一个点作为正方形左上角的坐标，然后从vBars中看向右可以扩展几列，从hBars中向下可以扩展几行，然后求出最大正方形面积。
     * 比如：
     * - 当前坐标 (i, j)，那么vBars中若存在i+1, i+2, i+3...i+k，那么就可以扩展k1列
     * - 当前坐标 (i, j)，那么hBars中若存在j+1, j+2, j+3...j+k，那么就可以扩展k2行
     * - 然后就可以计算出当前点可扩展的最大边长，然后由于正方形的性质，边长 = Math.min(h+1, w+1)，+1是因为删除n列可以形成n+1格
     * - 然后求出最大正方形面积，返回
     * 再然后，发现：
     * - 暴力枚举m,n没意义，因为根据上面分析，本质上变成了有几个连续的列可删除，几个连续的行可删除
     * - 然后边长= （最大可删除的列+1,最大可删除行+1)，然后求出面积
     * - 即本质上问题转换成了求未排序数组中最长连续子数组的长度，最终可优化为O(m+n)，不再需要排序，参考{@link LongestConsecutive}
     */
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        Arrays.sort(hBars);
        Arrays.sort(vBars);
        int h = 0, i = 0;
        while (i < hBars.length) {
            int ch = 1;
            while (i < hBars.length - 1 && hBars[i] == hBars[i + 1] - 1) {
                i++;
                ch++;
            }
            h = Math.max(h, ch);
            i++;
        }

        int w = 0, j = 0;
        while (j < vBars.length) {
            int cw = 1;
            while (j < vBars.length - 1 && vBars[j] == vBars[j + 1] - 1) {
                j++;
                cw++;
            }
            w = Math.max(w, cw);
            j++;
        }
        int ans = Math.min(h + 1, w + 1);
        return ans * ans;
    }
}
