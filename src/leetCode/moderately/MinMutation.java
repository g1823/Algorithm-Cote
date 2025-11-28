package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 433. 最小基因变化
 */
public class MinMutation {
    public static void main(String[] args) {
        String startGene = "AACCGGTT";
        String endGene = "AAACGGTA";
        String[] bank = {"AACCGATT", "AACCGATA", "AAACGATA", "AAACGGTA"};
        System.out.println(minMutation(startGene, endGene, bank));
    }

    /**
     * 【题目思路】
     * 本题要求计算从 startGene 变到 endGene 的最小基因变化次数。
     * 每次变化只能修改一个字符，且变化后的新基因必须存在于 bank 中。
     * 【思考过程】
     * 1. 由于每次变化只能修改 1 个字符，且需要找到最短路径，因此天然适合使用 BFS。
     * - 每层表示一次变化；
     * - 每个节点表示当前的基因字符串；
     * - 当某一层首次到达 endGene，即为最短变化次数。
     * 2. 每个基因字符串长度固定为 8，且字符集为 {A, C, G, T}。
     * 因此，对于当前字符串，可以依次修改每个位置，尝试 3 种可能变化（除当前字符以外的字母）。
     * 若变化后的字符串在 bank 中且未访问过，则入队。
     * 3. BFS 的层次 step 代表当前已发生的变化次数；
     * 当生成的 next 等于 endGene 时，返回 step + 1 即可。
     * 4. 为避免重复访问：
     * - 在入队时立即标记 visited；
     * - 每次修改字符后需恢复原字符，以免影响后续循环。
     * 【时间复杂度】
     * - 每个基因长度为 L（固定为 8），每个位置尝试 3 次变化；
     * 每个基因最多扩展 8×3=24 个状态；
     * - BFS 最多访问 bank.length + 1 个基因；
     * 整体复杂度为 O(L × 3 × N)，N 为 bank 大小。
     * 【空间复杂度】
     * - visited 集合与队列最多存储 N 个基因，O(N)。
     */
    public static int minMutation(String startGene, String endGene, String[] bank) {
        // 起点等于终点，变化次数为 0
        if (startGene.equals(endGene)) {
            return 0;
        }
        // 将基因库存入集合，便于快速判断存在性
        Set<String> set = new HashSet<>(Arrays.asList(bank));
        // 若终点基因不在库中，直接返回 -1
        if (!set.contains(endGene)) {
            return -1;
        }
        // BFS 层级计数
        int step = 0;
        // 基因可选字符
        char[] genes = {'A', 'C', 'G', 'T'};
        // 记录已访问的基因，防止重复入队
        Set<String> visited = new HashSet<>();
        visited.add(startGene);
        // BFS 队列初始化
        Queue<String> queue = new LinkedList<>();
        queue.offer(startGene);
        // 层序遍历
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                char[] chars = cur.toCharArray();
                // 尝试对每个字符进行变化
                for (int k = 0; k < chars.length; k++) {
                    char temp = chars[k];
                    // 每个位置有 3 种可能变化
                    for (char gene : genes) {
                        if (temp == gene) {
                            continue;
                        }
                        // 构造新基因
                        chars[k] = gene;
                        String next = new String(chars);
                        // 找到目标基因，返回变化次数
                        if (next.equals(endGene)) {
                            return step + 1;
                        }
                        // 若在基因库中且未访问过，入队
                        if (set.contains(next) && !visited.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                    // 恢复原字符，准备处理下一个位置
                    chars[k] = temp;
                }
            }
            // 扩展一层，变化次数加一
            step++;
        }
        // BFS 结束仍未到达目标，返回 -1
        return -1;
    }

}
