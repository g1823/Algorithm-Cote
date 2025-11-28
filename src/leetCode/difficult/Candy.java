package leetCode.difficult;

/**
 * @author: gj
 * @description: 135. 分发糖果
 */
public class Candy {

    public static void main(String[] args) {
        int[] ratings = {1, 3, 2, 2, 1};
        System.out.println(new Candy().candy(ratings));
    }

    /**
     * 分发糖果 - 贪心算法:
     * 思考过程：
     * 先考虑最直观的想法，如果找到评分最低的小朋友，让其只得到一个糖果，然后以其为中点向两侧扩展。
     * 但是出现了一个问题：
     * - 对于1332这种情况，从i=0（ratings[0] = 1全场最低）开始，糖果数为：1, 2, 1, 1
     * - 然而实际上，对于 下标2(ratings[2]=3) 和 下标3(ratings[2]=>2) 不满足要求，3>2，糖果却都为1
     * 继续思考，实际上这里只考虑了单侧的评分影响，没有考虑另一侧的影响，所以出现了问题
     * 本质上，每个位置的糖果数取决于其与左右两边的评分关系：
     * - 若大于左侧，则需要比左侧多1，否则为1；大于右侧，则需要比右侧多1，否则为1
     * - 两边都满足的情况下，就需要考虑两边，去较大的值
     * - 也就是说，只需要考虑到每个下标处于连续递增序列中的第i个（左右递增序列），那么至少要被发i个糖果
     * 解题思路：
     * 1. 初始化两个数组left和right，分别表示从左到右和从右到左的糖果分配规则
     * 2. left数组：从左遍历，如果当前孩子评分比左边高，则糖果数比左边多1
     * 3. right数组：从右遍历，如果当前孩子评分比右边高，则糖果数比右边多1
     * 4. 最终每个孩子的糖果数为left和right中的较大值
     * 时间复杂度：O(n)，空间复杂度：O(n)
     */
    public int candy(int[] ratings) {
        int result = 0;
        // 记录从做往右每个下标的连续递增数
        int[] left = new int[ratings.length];
        left[0] = 1;
        // 记录从右往左每个下标的连续递增数
        int[] right = new int[ratings.length];
        right[ratings.length - 1] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
            if (ratings[ratings.length - i - 1] > ratings[ratings.length - i]) {
                right[ratings.length - i - 1] = right[ratings.length - i] + 1;
            } else {
                right[ratings.length - i - 1] = 1;
            }
        }
        for (int i = 0; i < ratings.length; i++) {
            result += Math.max(left[i], right[i]);
        }
        return result;
    }
}
