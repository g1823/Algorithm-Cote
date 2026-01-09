package leetCode.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 1431. 拥有最多糖果的孩子
 */
public class KidsWithCandies {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        for (int i = 0; i < candies.length; i++) {
            max = Math.max(max, candies[i]);
        }
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < candies.length; i++) {
            if (candies[i] + extraCandies >= max) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        return result;
    }
}
