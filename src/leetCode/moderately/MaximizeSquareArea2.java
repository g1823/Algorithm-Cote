package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 2975. 移除栅栏得到的正方形田地的最大面积
 */
public class MaximizeSquareArea2 {

    /**
     * 求出横向和纵向所有可能的正方形边长，然后找出两边同时存在且最大的边长即可
     */
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        Set<Integer> hSideLengths = getAllSideLengths(hFences, m);
        Set<Integer> vSideLengths = getAllSideLengths(vFences, n);
        long ans = 0;
        for (Integer hSideLength : hSideLengths) {
            if (vSideLengths.contains(hSideLength)) {
                ans = Math.max(ans, hSideLength);
            }
        }
        if (ans == 0) {
            return -1;
        }
        return (int) ((ans * ans) % 1000000007);
    }

    private Set<Integer> getAllSideLengths(int[] fences, int border) {
        Set<Integer> sideLengths = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(border);
        for (int i = 0; i < fences.length; i++) {
            list.add(fences[i]);
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                sideLengths.add(list.get(j) - list.get(i));
            }
        }
        return sideLengths;
    }
}
