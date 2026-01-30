package leetCode.moderately;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: gj
 * @description: 2300. 咒语和药水的成功对数
 */
public class SuccessfulPairs {
    public static void main(String[] args) {
        int[] spells = new int[]{5, 1, 3};
        int[] potions = new int[]{1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(new SuccessfulPairs().successfulPairs(spells, potions, 7)));
    }

    /**
     * 二分查找
     * 计算每个咒语与药水成功配对的数量
     * 核心思路：
     * 1. 将药水排序，便于二分查找
     * 2. 将咒语按强度从小到大排序（弱→强），并记录原索引
     * 3. 对于弱咒语，需要强药水才能满足条件；对于强咒语，弱药水即可满足
     * 4. 使用固定右边界的策略：随着咒语变强，所需药水变弱，搜索范围向左收缩
     * 时间复杂度：O(m log m + n log n + n log m)，其中：
     * - m: 药水数量
     * - n: 咒语数量
     * - m log m: 排序药水
     * - n log n: 排序咒语索引
     * - n log m: 二分查找每个咒语
     * 空间复杂度：O(n)，用于存储排序后的咒语索引和结果
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int n = spells.length;
        int m = potions.length;

        // 1. 排序药水数组 - O(m log m)
        // 排序后便于使用二分查找快速找到满足条件的药水
        Arrays.sort(potions);

        // 2. 创建并排序咒语索引数组 - O(n log n)
        // 按照咒语强度从小到大排序，同时保留原始索引
        Integer[] spellIndices = new Integer[n];
        for (int i = 0; i < n; i++) {
            spellIndices[i] = i;  // 存储原始索引
        }
        // 按咒语值从小到大排序（弱咒语在前，强咒语在后）
        Arrays.sort(spellIndices, Comparator.comparingInt(a -> spells[a]));

        // 3. 初始化结果数组和搜索边界
        int[] ans = new int[n];
        // 固定右边界，初始为药水数组的最大索引
        int right = m - 1;

        // 4. 处理每个咒语（从弱到强） - O(n log m)
        for (int idx : spellIndices) {
            long spell = spells[idx];

            // 计算所需的最小药水值（向上取整）
            // 公式：spell * potion >= success  => potion >= success / spell
            // 使用向上取整避免浮点数运算
            long minPotion = (success + spell - 1) / spell;

            // 在药水数组的[0, right]范围内二分查找第一个 >= minPotion 的位置
            // 注意：lowerBound使用左闭右开区间[0, right+1)
            int pos = lowerBound(potions, 0, right + 1, minPotion);

            // 计算成功配对数量：从pos位置开始的所有药水都满足条件
            ans[idx] = m - pos;

            // 更新右边界：下一个咒语更强，只需要更弱的药水
            // 所以下次搜索的最大索引是当前找到位置的前一个位置
            // 注意：pos-1可能是负数，但二分查找能正确处理这种情况
            right = pos - 1;
        }

        return ans;
    }

    /**
     * 二分查找第一个大于等于目标值的位置（左闭右开区间）
     */
    private int lowerBound(int[] potions, int l, int r, long target) {
        // 标准的二分查找模板
        while (l < r) {
            // 防止溢出，等效于(l+r)/2
            int mid = l + (r - l) / 2;

            if (potions[mid] >= target) {
                // 当前元素满足条件，但可能有更小的满足条件的元素
                // 所以继续在左侧搜索（包含mid）
                r = mid;
            } else {
                // 当前元素太小，必须在右侧搜索（不包含mid）
                l = mid + 1;
            }
        }
        // 循环结束时，l==r，且指向第一个>=target的位置
        return l;
    }
}
