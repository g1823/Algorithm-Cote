package leetCode.simple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: gj
 * @description: 2215. 找出两数组的不同
 */
public class FindDifference {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2, 3, 3};
        int[] nums2 = new int[]{1, 1, 2, 2};
    }

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        for (int j : nums1) {
            set1.add(j);
        }
        Set<Integer> set2 = new HashSet<>();
        for (int j : nums2) {
            set2.add(j);
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(set1.stream().filter(x -> !set2.contains(x)).collect(Collectors.toList()));
        result.add(set2.stream().filter(x -> !set1.contains(x)).collect(Collectors.toList()));
        return result;
    }

}
