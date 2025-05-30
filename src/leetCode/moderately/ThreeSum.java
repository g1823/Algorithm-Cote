package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 15. 三数之和
 */
public class ThreeSum {
    public static void main(String[] args) {
        int[] nums = new int[]{-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
        List<List<Integer>> lists = threeSum2(nums);
        System.out.println(lists);
    }


    /**
     * 双指针 + 排序
     * 三数求和问题因为有三个变量，比较复杂，因此可以尝试减少一个变量。
     * 设三个数分别为a、b、c
     * 1、将原数组排序，使得原数组有序
     * 2、固定数a为数组下标i的值，i初始化为0
     * 3、因为数组从小到大排序，因此想要让a+b+c=0，那么b和c一定在下标i的右侧
     * 4、数b = nums[i+1] 数c = nums[n-1-i]
     * 5、然后进行判断:
     * - 如果a + b + c = 0，则将a、b、c加入到结果集中
     * - 如果a + b + c > 0，则说明b + c太大，需要减小b + c的值，因此需要将c向左移动一位
     * - 如果a + b + c < 0，则说明b + c太小，需要增大b + c的值，因此需要将b向右移动一位
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 你可以把去重看作是“去掉结果中的重复”，而不是“去掉搜索路径上的重复”。提前去重就是直接把路径堵死了，根本不给它尝试的机会，自然会漏结果。
            // 所以需要在搜索完当前元素后再去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 跳过重复b和c
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    /**
     * 错误解法，思路：
     * 将原数组排序，理论上来说，最小的和最大的加一个中等大小的比较容易凑出0
     * 因此两个指针从两侧向中间移动，如果二者相加的负值存在于原数组中，则取出，然后指针向中间移动
     * 但是没有考虑到一个更小的负数可以通过两个中间大小的数组合为0，而不必要使用最大的正数进行组合。
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return null;
        }
        int[] copyNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copyNums);
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        Set<String> set = new HashSet<>();
        for (int copyNum : copyNums) {
            if (map.containsKey(copyNum)) {
                map.put(copyNum, map.get(copyNum) + 1);
            } else {
                map.put(copyNum, 1);
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        int l = 0, r = copyNums.length - 1;
        while (l < r) {
            int three = -(copyNums[l] + copyNums[r]);
            while (l < r && copyNums[l] == copyNums[l + 1]) {
                l++;
            }
            while (l < r && copyNums[r] == copyNums[r - 1]) {
                r--;
            }
            if (map.containsKey(three)) {
                if (isValid(l, r, three, copyNums, set, map)) {
                    result.add(Arrays.asList(copyNums[l], copyNums[r], three));
                }
            }
            if (l + 1 < r) {
                int newThree = -(copyNums[l + 1] + copyNums[r]);
                if (map.containsKey(newThree)) {
                    if (isValid(l + 1, r, newThree, copyNums, set, map)) {
                        result.add(Arrays.asList(copyNums[l + 1], copyNums[r], newThree));
                    }
                }
                newThree = -(copyNums[l] + copyNums[r - 1]);
                if (map.containsKey(newThree)) {
                    if (isValid(l, r - 1, newThree, copyNums, set, map)) {
                        result.add(Arrays.asList(copyNums[l], copyNums[r - 1], newThree));
                    }
                }
            }
            l++;
            r--;
        }
        return result;
    }

    public static boolean isValid(int l, int r, int three, int[] nums, Set<String> set, Map<Integer, Integer> map) {
        if (three == nums[l] || three == nums[r]) {
            if (nums[l] == nums[r]) {
                if (map.get(three) >= 3) {
                    return isValid2(nums[l], nums[r], three, set);
                } else {
                    return false;
                }
            } else {
                if (map.get(three) >= 2) {
                    return isValid2(nums[l], nums[r], three, set);
                } else {
                    return false;
                }
            }
        } else {
            return isValid2(nums[l], nums[r], three, set);
        }
    }

    public static boolean isValid2(int l, int r, int three, Set<String> set) {
        int[] list = new int[]{l, r, three};
        Arrays.sort(list);
        String key = Arrays.toString(list);
        if (!set.contains(key)) {
            set.add(key);
            return true;
        } else {
            return false;
        }
    }
}
