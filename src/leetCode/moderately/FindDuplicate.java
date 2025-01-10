package leetCode.moderately;

import leetCode.simple.HasCycle;

/**
 * @author: gj
 * @description: 287. 寻找重复数
 */
public class FindDuplicate {
    public static void main(String[] args) {
        int[] data = new int[]{3, 1, 3, 4, 2};

        System.out.println(new FindDuplicate().findDuplicate3(data));
    }

    /**
     * 题目说明nums数组数据在1-n区间，即1-数组长度之间，因此可以用一个同样长的数组模拟哈希表记录数据。
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int[] d = new int[nums.length + 1];
        for (int num : nums) {
            if (d[num] == 1) return num;
            else d[num] = 1;
        }
        return 0;
    }

    /**
     * 二分查找
     * 题目说明nums数组数据在1-n区间，即1-数组长度之间。
     * 那么就会有如下规律：
     * 定义 cnt[i] 表示 nums 数组中小于等于 i 的数有多少个，假设我们重复的数是 target，
     * 那么 [1,target−1]里的所有数满足 cnt[i]≤i，[target,n] 里的所有数满足 cnt[i]>i，具有单调性。
     * 验证是否正确：
     * 1、如果测试用例的数组中 target 出现了两次，其余的数各出现了一次，这个时候肯定满足上文提及的性质，因为小于 target 的数 i 满足 cnt[i]=i，大于等于 target 的数 j 满足 cnt[j]=j+1。
     * 2、如果测试用例的数组中 target 出现了三次及以上，那么必然有一些数不在 nums 数组中了，这个时候相当于我们用 target 去替换了这些数，我们考虑替换的时候对 cnt[] 数组的影响。
     * 如果替换的数 i 小于 target ，那么 [i,target−1] 的 cnt 值均减一，其他不变，满足条件。如果替换的数 j 大于等于 target，那么 [target,j−1] 的 cnt 值均加一，其他不变，亦满足条件。
     * 因此只需要找到 ** cnt中小于等于下标的位置和cnt数组中大于下标的位置临界值即可 **：
     * 而因为具有单调性，可以使用二分查找
     *
     * @param nums
     * @return
     */
    public int findDuplicate2(int[] nums) {
        int result = -1, l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2, t = 0;
            for (int num : nums) {
                if (num <= mid) t++;
            }
            if (t <= mid) {
                l = mid + 1;
            }
            if (t > mid) {
                r = mid - 1;
                result = mid;
            }
        }
        return result;
    }

    /**
     * 双指针：
     * 题目说明nums数组数据在1-n区间，即1-数组长度之间。
     * 创建一个图，由nums数组的下标i指向下标为nums[i]的值。
     * 由于一定有重复的值，那么就是说至少有两个下标（j、k）指向同一个下标的值 target，那么一定会有环
     * - nums[i] = i ，那么自己就是一个环，如果i不重复，那么就不会有其他下标指向i，就不会进入这个环，因此自环不会影响
     * - 因为 1<= nums[i] <= n，那么nums[0] != 0，从下标0开始一定会进入某个下标下。
     * 此时就可以模仿环形链表，找到对应的环也就找到了重复的数：{@link HasCycle}{@link DetectCycle}
     *
     * @param nums
     * @return
     */
    public int findDuplicate3(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        fast = 0;
        while (fast != slow) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }
}
