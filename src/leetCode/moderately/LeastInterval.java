package leetCode.moderately;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: gj
 * @description: 621. 任务调度器
 */
public class LeastInterval {

    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'C', 'D', 'D', 'E'};
        int n = 2;
        LeastInterval leastInterval = new LeastInterval();
        int result = leastInterval.leastInterval(tasks, n);
        System.out.println(result);
    }

    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        Map<Character, int[]> tasksMap = new HashMap<>(16);
        for (int i = 0; i < tasks.length; i++) {
            if (tasksMap.containsKey(tasks[i])) {
                tasksMap.get(tasks[i])[0]++;
            } else {
                tasksMap.put(tasks[i], new int[]{1, 0});
            }
        }
        int i = 1;
        while (!tasksMap.isEmpty()) {
            for (Map.Entry<Character, int[]> entry : tasksMap.entrySet()) {
                int j = entry.getValue()[1];
                if (j == 0 || j + n < i) {
                    int[] value = entry.getValue();
                    value[1] = i;
                    value[0] = value[0] - 1;
                    if (value[0] == 0) {
                        tasksMap.remove(entry.getKey());
                    }
                    break;
                }
            }
            i++;
        }
        return i - 1;
    }


    // TODO:方案一错误，需要重新思考
    public int leastInterval2(char[] tasks, int n) {
       return 0;
    }
}
