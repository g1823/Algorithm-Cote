package leetCode.moderately;

/**
 * @author: gj
 * @description: 904. 水果成篮
 */
public class TotalFruit {

    public static void main(String[] args) {
        int[] fruits = {1,0,3,4,3};
        System.out.println(new TotalFruit().totalFruit(fruits));
    }

    /**
     * 本题目翻译一下就是：
     * 求数组中仅包含两种不同元素的最长连续子数组长度
     * 解题思路：
     * - 本质是“最长连续子数组中最多包含 2 种不同元素”
     * - 常规滑动窗口会用 Map 记录元素频次，这里用更高效的状态记录法
     * - 维护：
     *   1. 两种水果类型（typeA, typeB）
     *   2. 对应的数量（countA, countB）
     *   3. 最近连续相同水果的类型（lastFruit）及其长度（lastCount）
     * - 遍历数组：
     *   1. 若当前水果属于篮子内的两种之一，则数量加 1
     *   2. 若遇到第三种水果：
     *      - 保留最近连续的 lastFruit 这一种
     *      - 将另一种替换为当前水果
     *      - 数量更新：保留种类数量 = lastCount，新种类数量 = 1
     *   3. 更新最近连续水果信息（lastFruit, lastCount）
     *   4. 更新最大长度（countA + countB）
     */
    public int totalFruit(int[] fruits) {
        if (fruits.length <= 2) {
            return fruits.length;
        }
        // 两种水果类型
        int typeA = -1, typeB = -1;
        // 对应数量
        int countA = 0, countB = 0;
        // 最近连续相同水果
        int lastFruit = -1, lastCount = 0;
        int maxLen = 0;
        for (int fruit : fruits) {
            if (fruit == typeA || typeA == -1) {
                typeA = fruit;
                countA++;
            } else if (fruit == typeB || typeB == -1) {
                typeB = fruit;
                countB++;
            } else {
                // 遇到第三种水果
                if (lastFruit == typeA) {
                    typeB = fruit;
                    countB = 1;
                    countA = lastCount;
                } else {
                    typeA = fruit;
                    countA = 1;
                    countB = lastCount;
                }
            }

            // 更新 lastFruit 和 lastCount
            if (fruit == lastFruit) {
                lastCount++;
            } else {
                lastFruit = fruit;
                lastCount = 1;
            }

            maxLen = Math.max(maxLen, countA + countB);
        }

        return maxLen;
    }
}
