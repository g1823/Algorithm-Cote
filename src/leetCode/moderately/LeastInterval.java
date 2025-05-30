package leetCode.moderately;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: gj
 * @description: 621. 任务调度器
 */
public class LeastInterval {

    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n = 2;
        LeastInterval leastInterval = new LeastInterval();
        int result = leastInterval.leastInterval3(tasks, n);
        System.out.println(result);
    }

    /**
     * 错误
     *
     * @param tasks
     * @param n
     * @return
     */
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


    /**
     * 计算完成所有任务所需的最短时间，任务之间需要冷却时间 n。
     * 该方法首先统计每个任务的出现次数，然后通过贪心算法处理任务。
     * 每一轮处理 n+1 个任务，尽量选择剩余次数最多的任务。
     * 如果当前任务的剩余次数等于下一轮的最小次数，则增加冷却时间。
     * 最后，计算剩余任务的最大值及其出现次数，确定最终的最短时间。
     *
     * @param tasks 任务数组，每个元素表示一个任务
     * @param n     两个相同任务之间的冷却时间
     * @return 完成所有任务所需的最短时间
     */
    public int leastInterval2(char[] tasks, int n) {
        // 如果任务数组为空或长度为0，直接返回0
        if (tasks == null || tasks.length == 0) {
            return 0;
        }

        // 使用HashMap统计每个任务的出现次数，数组[0]表示次数，[1]表示上次执行时间
        Map<Character, int[]> tasksMap = new HashMap<>(16);
        for (int i = 0; i < tasks.length; i++) {
            if (tasksMap.containsKey(tasks[i])) {
                tasksMap.get(tasks[i])[0]++;
            } else {
                tasksMap.put(tasks[i], new int[]{1, 0});
            }
        }

        // 将任务的出现次数提取到一个列表中
        List<Integer> list = tasksMap.values().stream().map(x -> x[0]).collect(Collectors.toCollection(LinkedList::new));
        int i = 0, z = 0;

        // 当列表中的任务数量大于 n+1+z 时，继续处理
        while (list.size() > n + 1 + z) {
            // 按任务出现次数降序排序
            list.sort(Comparator.reverseOrder());

            // 计算当前轮次需要处理的任务次数差值
            int k = list.get(n) - list.get(n + 1) + 1;

            // 处理前 n+1 个任务
            for (int j = 0; j <= n; j++) {
                // 如果当前任务的剩余次数等于下一轮的最小次数，则增加冷却时间
                if (list.get(j) == k) {
                    z++;
                }
                // 减少当前任务的剩余次数
                list.set(j, list.get(j) - k);
            }

            // 增加已处理的时间
            i += k;
        }

        // 过滤出剩余次数大于0的任务
        List<Integer> list2 = list.stream().filter(x -> x > 0).collect(Collectors.toList());
        int max = 0, temp = 0;

        // 找到剩余次数最多的任务的最大值
        for (Integer integer : list2) {
            max = Math.max(max, integer);
        }

        // 计算剩余次数最多的任务的数量
        for (Integer integer : list2) {
            temp += integer == max ? 1 : 0;
        }

        // 返回完成所有任务所需的最短时间
        return max == 0 ? i * (n + 1) : (i + max - 1) * (n + 1) + temp;
    }


    /**
     * 根据题目要求，task只有A—Z大写字母，统计次数可以直接使用task - 'A'作为下标统计。
     * 获取最大出现次数maxCount，因为两个相同任务间，时间间隔为n，那么最快都需要 (maxCount - 1) * (n+1) + 1次，
     * 在每一轮n+1的任务内，可以穿插其他任务，因为其他的任务次数不超过maxCount，所以不会超过(maxCount - 1) * (n+1) + 1次。
     * 但是需要考虑到任务种类数超过了n+1，也就是每一轮不会把所有任务都执行一遍。
     * 进一步思考，任务种类数超过n+1，按照上述插入任务的策略，那么两个相同任务执行时，中间间隔就一定超过了n,符合题意。
     * 没有超出：
     * 如果我们没有填「超出」了 n+1 列，那么图中存在 0 个或多个位置没有放入任务，由于位置数量为 (maxExec−1)(n+1)+maxCount，因此有：
     * ∣task∣<(maxExec−1)(n+1)+maxCount
     * 超出：
     * 如果我们填「超出」了 n+1 列，那么同理有：
     * ∣task∣>(maxExec−1)(n+1)+maxCount
     * 因此，在任意的情况下，需要的最少时间就是 (maxExec−1)(n+1)+maxCount 和 ∣task∣ 中的较大值。
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval3(char[] tasks, int n) {
        char[] data = new char[26];
        for (char task : tasks) {
            data[task - 'A']++;
        }
        Arrays.sort(data);
        int maxCount = data[25];
        int maxCountNum = 1;
        for (int i = 24; i >= 0; i--) {
            if (data[i] == maxCount) {
                maxCountNum++;
            } else {
                break;
            }
        }
        int result = (maxCount - 1) * (n + 1) + maxCountNum;
        return Math.max(result, tasks.length);
    }


}
