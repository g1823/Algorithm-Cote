package leetCode.moderately;

/**
 * @author: gj
 * @description: 134. 加油站
 */
public class CanCompleteCircuit {

    /**
     * 暴力枚举：
     * 尝试以每个下标作为起点，判断能否跑完一圈。
     * 通过int index = j % gas.length;计算出下一个下标
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        for (int i = 0; i < gas.length; i++) {
            int g = 0;
            for (int j = i; j < i + gas.length; j++) {
                int index = j % gas.length;
                g += gas[index];
                if (g < cost[index]) {
                    break;
                } else {
                    g -= cost[index];
                }
                if (j == i + gas.length - 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 解题思路：
     * 1. 暴力枚举：尝试以每个加油站为起点，模拟行驶一圈。时间复杂度 O(n²)。
     * 2. 优化思路：
     * - 如果总油量 sum(gas) < 总消耗 sum(cost)，肯定无法绕行一圈，直接返回 -1。
     * - 关键洞察：如果从起点 i 能到达 j 但无法到达 j+1，那么 i 到 j 之间的任意点作为起点都无法到达 j+1。
     * 因为 i 到 j 段的净收益都是非负的（否则早就失败了），所以从中间点出发只会获得更少的初始油量。
     * - 因此，当当前剩余油量 cur < 0 时，可以直接将起点跳到 i+1，并重置 cur。
     * 3. 最终解法：一次遍历，时间复杂度 O(n)。
     *
     * @param gas  每个加油站的油量
     * @param cost 从当前站到下一站的油耗
     * @return 能够绕行一圈的起始加油站索引，如果不存在则返回 -1
     */
    public int canCompleteCircuit2(int[] gas, int[] cost) {
        // 总剩余油量（总油量 - 总消耗）
        int total = 0;
        // 从当前起点开始的累计剩余油量
        int cur = 0;
        // 候选起点索引
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            total += gas[i] - cost[i];
            cur += gas[i] - cost[i];

            // 如果当前剩余油量小于0，说明从当前起点无法到达下一站
            if (cur < 0) {
                start = i + 1;
                cur = 0;
            }
        }

        // 如果总剩余油量非负，则存在解（start即为答案）；否则无解
        return total >= 0 ? start : -1;
    }
}
