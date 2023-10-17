package mianshijingdian150;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:gj
 * @DateTime:2023/10/17 20:31
 * @description: 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 **/
public class Five {
    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println(new Five().majorityElement(nums));
    }

    /**
     * 哈希表维护最大数量：
     * 使用一个map存储每个元素出现的次数，然后直接取出现次数最多的
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int result = nums[0], maxCount = 1;
        for (int num : nums) {
            if (map.containsKey(num)) {
                Integer count = map.get(num);
                map.put(num, count + 1);
                if (count + 1 > maxCount) {
                    result = num;
                    maxCount = count + 1;
                }
            } else {
                map.put(num, 1);
            }
        }
        return result;
    }

    /**
     * Boyer-Moore 投票算法：
     * 遍历数组，当前数与基准数相同则count+1，不相同则-1
     * 当count=0就替换基准数为当前数
     * 因为最多的数数量大于n/2，因此最后count一定为正数，且其可以抵消掉所有的其他数字，最后留下的一定是它
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int majorityElement2(int[] nums) {
        // result初始时值不能是0，因为这相当于原数组中的0投了一次票，所以需要为Integer类型，置为null
        Integer result = null;
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                result = num;
            }
            count += (result == num) ? 1 : -1;
        }
        return result;
    }

    /**
     * 排序：
     * 因为所求数数量大于n/2，因此排序过后，这个数一定在n/2的位置上
     * 时间复杂度：O(nlog(n))
     * 空间复杂度：O(log(n))。如果自己编写堆排序，则只需要使用 O(1)的额外空间。
     */
    public int majorityElement3(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 分治法，暂未实现
     * @param nums
     * @return
     */
    public int majorityElement4(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
