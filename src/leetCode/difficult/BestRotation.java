package leetCode.difficult;

/**
 * @description: 798. 得分最高的最小轮调
 */
public class BestRotation {

    public static void main(String[] args) {
        BestRotation bestRotation = new BestRotation();
        int[] nums = {2, 3, 1, 4, 0};
        System.out.println(bestRotation.bestRotation(nums));
    }

    /**
     * 暴力（超时）
     */
    public int bestRotation(int[] nums) {
        int n = nums.length;
        // 2、接下来进行轮换，轮换k，就是对前k个元素的下标 + k ，对剩余元素下标 - k
        int maxScore = Integer.MIN_VALUE;
        int maxIndex = 0;
        for (int k = 0; k < n; k++) {
            int curScore = 0;
            for (int i = 0; i < n; i++) {
                // 小于k的元素先移到最后，在回退k，然后再加上自己原来坐标就是最终位置的索引
                if (i < k && nums[i] <= n - k + i) {
                    curScore++;
                }
                if (i >= k && nums[i] <= i - k) {
                    curScore++;
                }
            }
            if (curScore > maxScore) {
                maxScore = curScore;
                maxIndex = k;
            }
        }
        return maxIndex;
    }


    /**
     * 差分：
     * 1、根据暴力中可以发现，每个下标对应的元素，符合条件的k的取值其实是两个连续范围
     * - 当i<=k时，范围为nums[i] <= i - k     =>  k <= i - nums[i]，也就是 0 <= k <= i - nums[i]。 上界i
     * - 当i>k时，范围为nums[i] <= n - k + i  =>  k <= n + i - nums[i]，也就是 i < k <= n + i - nums[i]。 下界i+1, 上界n
     * 2、接下来问题就转换成知道一组有效取值范围，如何计算这些范围重叠次数最多情况下的最小取值了。
     * - 如果一个一个K计算，依旧会退化为O(n^2)，每个k都需要计算n次
     * 3、转换思考方式
     * - 前面得到了可每个i符合条件的k取值范围，那么在该范围内，该元素就会贡献1个得分。
     * - 那么就可以使用差分来做，每个元素都把自己得分区间放到差分数组上，起始位置 +1， 结束位置 -1
     * - 只需要从前往后扫描，记录最大值即可
     * 相较于暴力：
     * 1. 消除重复判断：暴力里每个元素在 n 个 k 下要被问 n 次，但它得不得分其实只取决于"k 是否落在我的一段区间"，这个区间事先一次算好，不用每次重推。
     * 2. 区间批量标记代替逐点累加：一个区间长度可能是 O(n)，逐点加就白干；改成只记端点 +1/-1（O(1)），最后用一次前缀和把所有区间的贡献一次性摊到每个 k 上。
     */
    public int bestRotation2(int[] nums) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            // 计算i<=k的范围
            if (i - value >= 0) {
                diff[0]++;
                // 题目说明了nums[i] >= 0
                diff[i - value + 1]--;
            }
            // 计算i>k的范围
            if (i + n - value > i && i + 1 < n) {
                diff[i + 1]++;
                // 上界截断到 n-1
                int end = Math.min(i + n - value, n - 1);
                // 结束标记放在上界后一位
                diff[end + 1]--;
            }
        }
        int maxIndex = 0;
        int maxScore = Integer.MIN_VALUE;
        int curScore = 0;
        for (int i = 0; i < diff.length; i++) {
            curScore += diff[i];
            if (curScore > maxScore) {
                maxScore = curScore;
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
