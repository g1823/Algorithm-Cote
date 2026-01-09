package leetCode.moderately;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 137. 只出现一次的数字 II TODO
 */
public class SingleNumber2 {
    /**
     * 最简单的解法，hash表记录每个数出现的次数，最后返回出现一次的数
     * 时间复杂度：O(n)
     * 空间复杂度：O(n),hash表最多有 n/3 + 1 个元素
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            Integer i = map.getOrDefault(num, 0);
            map.put(num, i + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * 优化解法:
     * 由于重复数字出现此时为3，不是偶数次，显然不能直接通过异或操作去除重复数字了
     * 那么，换一个思路，对数字的每一位进行累加，出现三次则取余一次，这样最后留下来的该位的值就是单独出现数字的该位的值了
     * 时间复杂度：O(nlogC)，C为2^32，Int类型只有32位，也就是32*n，即O(n)
     * 空间复杂度：O(1)
     */
    public int singleNumber2(int[] nums) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int sum = 0;
            // 计算所有数字在第i位上为1个数
            for (int num : nums) {
                sum += (num >> i) & 1;
            }
            // 通过模3取余(sum % 3) ，得到该位上单独出现的数字， 然后左移i位(<<i )，再通过按位或运算符(|=)赋给res的第i位
            res |= (sum % 3) << i;
        }
        return res;
    }

    /**
     * 解题思路：使用位状态机实现“每个数字对 3 取模统计”。
     * ============================================================================
     * 一、背景说明
     * 所有数字均出现 3 次，只有一个数字出现 1 次。
     * 我们希望不记录每个 bit 的计数，而是让每个位自动在 0→1→2→0 的模 3 状态之间流动。
     * <p>
     * 为此，用两个变量 ones、twos 保存“某位出现次数 mod 3 = 1 或 2”的集合：
     * (ones=0, twos=0) → 出现次数 mod 3 = 0
     * (ones=1, twos=0) → 出现次数 mod 3 = 1
     * (ones=0, twos=1) → 出现次数 mod 3 = 2
     * 非法状态：(ones=1, twos=1) 不存在于真实计数中。
     * <p>
     * ============================================================================
     * 二、构造真值表（对单个位 bit）
     * <p>
     * 设：
     * o = ones 原值
     * t = twos 原值
     * x = 当前数字该位（0 或 1）
     * <p>
     * -------- new_ones 真值表 --------
     * o  t  x | new_o    解释
     * ---------|-------
     * 0  0  0 |   0     保持 0 次
     * 0  0  1 |   1     第一次出现
     * 1  0  0 |   1     保持出现 1 次
     * 1  0  1 |   0     第二次出现：从 ones → twos，因此 new_ones=0
     * 0  1  0 |   0     保持出现 2 次，不影响 ones
     * 0  1  1 |   0     第三次出现：twos 变 0，因此 ones=0
     * 1  1  * | 非法   不应出现
     * <p>
     * 从真值表可总结 new_ones 为 1 的行：
     * 1)  o=0,t=0,x=1
     * 2)  o=1,t=0,x=0
     * <p>
     * 写成布尔表达式：
     * new_o = (!o & !t & x) | (o & !t & !x)
     * 提取公共因子 !t：
     * new_o = !t & ( (!o & x) | (o & !x) )
     * 括号部分为 XOR：
     * new_ones = (o XOR x) & (~t)
     * <p>
     * 代回变量名：
     * new_ones = (ones ^ num) & ~twos
     * <p>
     * ============================================================================
     * 三、推导 new_twos（关键在于必须基于“更新后”的 new_ones）
     * <p>
     * -------- new_twos 真值表 --------
     * o  t  x | new_t    解释
     * ---------|-------
     * 0  0  0 |   0
     * 0  0  1 |   0     第一次出现不会进 twos
     * 1  0  0 |   0
     * 1  0  1 |   1     第二次出现：ones 清零，twos=1
     * 0  1  0 |   1     保持出现 2 次
     * 0  1  1 |   0     第三次出现：twos 清零
     * 1  1  * | 非法
     * <p>
     * 如果直接用 old ones 推导 twos，表达式非常复杂，并且容易产生非法状态。
     * <p>
     * 但注意状态转换顺序：
     * 第二次出现时，会“把 ones 的 1 交给 twos”。
     * 第三次出现时，两者都清零。
     * <p>
     * 因此 twos 的更新必须基于 new_ones（已更新后的 ones）。
     * <p>
     * 经过同样的 XOR + 屏蔽操作可得：
     * new_twos = (twos ^ x) & ~new_ones
     * <p>
     * 因此最终：
     * ones = (ones ^ num) & ~twos;
     * twos = (twos ^ num) & ~ones;   // 此时 ones 是“new_ones”
     * <p>
     * <p>
     * ============================================================================
     * 四、为什么必须“先算 ones，再算 twos”？
     * <p>
     * 原因：状态机的真实流向是：
     * 0次 → 1次 → 2次 → 0次
     * <p>
     * 而这一流程的体现是：
     * 第二次出现时：ones 应该先被清零，然后 twos 才设置为 1。
     * 第三次出现时：ones 已为 0，twos 才能准确清零。
     * <p>
     * 如果先更新 twos，再更新 ones，会发生：
     * - 第二次出现时 twos 可能基于旧 ones 得到错误结果
     * - 第三次出现会出现 ones=1, twos=1 的非法组合
     * <p>
     * 所以正确顺序必须是：
     * 1) ones = new_ones
     * 2) twos = new_twos（基于 new_ones）
     * <p>
     * <p>
     * ============================================================================
     * 五、最终结果
     * 最终 ones 中保留的就是出现次数为 1 的数字的所有 bit。
     * <p>
     * <p>
     * ============================================================================
     * 六、复杂度
     * 每个数字做常数次位运算：时间 O(n)，空间 O(1)。
     */
    public int singleNumber3(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            // 更新 ones（new_ones）
            ones = (ones ^ num) & ~twos;
            // 更新 twos（new_twos），注意 ones 已是 new_ones
            twos = (twos ^ num) & ~ones;
        }
        // 最终 ones 中保留的就是只出现一次的数
        return ones;
    }

}
