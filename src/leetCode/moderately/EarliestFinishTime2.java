package leetCode.moderately;

import leetCode.simple.EarliestFinishTime;

/**
 * @description: 3635. 最早完成陆地和水上游乐设施的时间 II
 */
public class EarliestFinishTime2 {

    /**
     * 跟3633. 最早完成陆地和水上游乐设施的时间 I要求一模一样，只是问题规模变大，不能O(m*n)了
     * {@link EarliestFinishTime#earliestFinishTime(int[], int[], int[], int[])}
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
        for (int i = 0; i < secondStartTime.length; i++) {
            result = Math.min(
                    result,
                    Math.max(firstMinFinishTime, secondStartTime[i]) + secondDuration[i]
            );
        }
        return result;
    }
}
