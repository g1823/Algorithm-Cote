package leetCode.simple;

/**
 * @description: 3633. 最早完成陆地和水上游乐设施的时间 I
 */
public class EarliestFinishTime {

    /**
     * 贪心
     * 思考过程：
     * 最开始容易想到先确定第一个游玩的项目，然后再确定第二个游玩的项目。
     * 如果直接枚举：
     * - 先玩陆地项目，再玩水上项目；
     * - 先玩水上项目，再玩陆地项目；
     * 并且对于第一类项目和第二类项目都进行两层遍历，那么时间复杂度为 O(n * m)。
     * 进一步观察：
     * 假设当前先游玩第一类项目，其结束时间为：finish1 = start1 + duration1
     * 然后游玩第二类项目：finish2 = max(finish1, start2) + duration2
     * 对于固定的第二类项目来说：finish2 = max(finish1, start2) + duration2
     * 其中：
     * - duration2 为常量；
     * - start2 为常量；
     * - finish1 越大，max(finish1, start2) 也不会变小。
     * 因此：
     * 如果存在两个第一类项目：
     * finish1A < finish1B
     * 那么一定有：
     * max(finish1A, start2) <= max(finish1B, start2)
     * 即：
     * 选择更晚结束的第一类项目，不可能让最终完成时间更早。
     * 所以对于第一类项目而言：
     * 只需要保留最早结束时间即可，其余项目一定不会更优。
     * 这样问题就转换为：
     * 1、求第一类项目最早结束时间 firstMinFinishTime；
     * 2、枚举第二类项目，计算最终完成时间；
     * 3、取最小值。
     * 状态转移：
     * finalFinishTime = max(firstMinFinishTime, secondStartTime[i]) + secondDuration[i]
     * 其中：
     * - 如果 secondStartTime[i] <= firstMinFinishTime，说明第二个项目已经开放，可以立即开始；
     * - 如果 secondStartTime[i] > firstMinFinishTime，说明第二个项目尚未开放，需要等待开放后再开始。
     * 注意点：
     * 1、不能直接计算：secondStartTime[i] + secondDuration[i]
     * 因为游客必须先完成第一个项目，第二个项目的实际开始时间应该是：max(firstMinFinishTime, secondStartTime[i])
     * 例如：
     * firstMinFinishTime = 10
     * secondStartTime = 5
     * secondDuration = 3
     * 实际结束时间：
     * max(10, 5) + 3 = 13
     * 而不是：
     * 5 + 3 = 8
     * 2、不能加判断：
     * secondStartTime[i] <= firstMinFinishTime
     * 因为题目允许等待。
     * 例如：
     * firstMinFinishTime = 5
     * secondStartTime = 100
     * secondDuration = 1
     * 此时虽然第二个项目还未开放，
     * 但仍然可以等待到 100 时开始游玩，
     * 最终完成时间为：
     * 100 + 1 = 101
     * 因此必须枚举所有第二类项目。
     * 3、本题贪心成立的关键在于：
     * 对于固定的第二类项目：
     * max(finish1, start2)
     * 随 finish1 的增大单调不减。
     * 因此第一类项目只保留最早结束时间即可，
     * 不需要枚举所有第一类项目。
     * 时间复杂度：
     * O(n + m)
     * 空间复杂度：
     * O(1)
     */
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int landFirstTime = getEarliestFinishTime(landStartTime, landDuration, waterStartTime, waterDuration);
        int waterFirstTime = getEarliestFinishTime(waterStartTime, waterDuration, landStartTime, landDuration);
        return Math.min(landFirstTime, waterFirstTime);
    }

    private int getEarliestFinishTime(int[] firstStartTime, int[] firstDuration, int[] secondStartTime, int[] secondDuration) {
        // 获取先游玩的项目的最小的结束时间
        int firstMinFinishTime = firstStartTime[0] + firstDuration[0];
        for (int i = 1; i < firstStartTime.length; i++) {
            int finishTime = firstStartTime[i] + firstDuration[i];
            if (finishTime < firstMinFinishTime) {
                firstMinFinishTime = finishTime;
            }
        }
        // 先游玩的项目最早结束时间已定，遍历后游玩项目，计算最小时间
        int result = Integer.MAX_VALUE;
        for (int i= 0; i < secondStartTime.length; i++) {
            result = Math.min(
                    result,
                    Math.max(firstMinFinishTime, secondStartTime[i]) + secondDuration[i]
            );
        }
        return result;
    }
}
