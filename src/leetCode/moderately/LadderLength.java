package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 127. 单词接龙
 */
public class LadderLength {

    /**
     * 双向广度优先搜索（Bidirectional BFS）解决单词接龙问题。
     *
     * 问题抽象:
     * 题目要求从 beginWord 经过若干次单词变换到达 endWord，每次只能改变一个字母且结果必须存在于 wordList。
     * 这可以抽象成一个无权图的最短路径问题：
     * - 每个单词是一个节点；
     * - 若两个单词只差一个字母，则存在一条边；
     * - 目标是找到从 beginWord 到 endWord 的最短路径。
     * 因此，可以使用 广度优先搜索（BFS） 来求解。
     *
     * 单边->双边：
     * 仅单边：如果只从 beginWord 开始普通 BFS：
     * - 每次扩展都会生成最多 26 × L（L 为单词长度）的新节点；
     * - 如果字典很大（例如几千个单词），搜索空间会迅速膨胀。
     * 双边：
     * 双向 BFS 的思路是：
     * - 同时从 起点 和 终点 向中间扩展；
     * - 当两边搜索相遇时，即可得到最短路径。
     * 这种做法的关键在于：
     * BFS 的搜索范围随层数呈指数级增长，从两边各扩展 k 层，比从一边扩展 2k 层，访问节点数少得多。
     * 因此，双向 BFS 能极大减少搜索空间。
     *
     * 从较小队列扩展的优化
     * 每一轮循环时，两个搜索边界中：
     * - 可能一边已经扩展得很广；
     * - 另一边仍较小。
     * 为了让每次扩展代价最小，应始终从 节点更少的一方 扩展。这能保持搜索的平衡性，进一步降低总遍历节点数。
     * 例如：若 begin 边有 1000 个节点，end 边只有 5 个节点，
     * 从 end 边扩展显然代价更低。
     *
     * 思路：
     * 1. 将每个单词看作图中的节点，若两个单词只差一个字母，则两节点相连。
     * 2. 使用双向 BFS，从 beginWord 和 endWord 同时开始搜索。
     * 3. 每次选择当前节点较少的一侧进行扩展，降低搜索复杂度。
     * 4. 当两侧搜索相遇时，返回两侧步数之和加 2，即为最短转换序列长度。
     * 算法特点：
     * - 时间复杂度显著优于单向 BFS。
     * - 双向扩展确保搜索空间最小化。
     * - 动态选择较小队列扩展可进一步优化性能。
     *
     * @param beginWord 起始单词
     * @param endWord   目标单词
     * @param wordList  单词字典
     * @return 最短转换序列的长度；若不存在则返回 0
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 若目标单词不在字典中，不可能到达
        if (!wordList.contains(endWord)) {
            return 0;
        }
        // 起点与终点相同，直接返回 1
        if (beginWord.equals(endWord)) {
            return 1;
        }
        // 预生成 26 个字母数组，用于构造邻接单词
        char[] chars = new char[26];
        for (char c = 'a'; c <= 'z'; c++) {
            chars[c - 'a'] = c;
        }
        // 将 wordList 转为 HashSet，提升 contains 查询效率
        Set<String> wordSet = new HashSet<>(wordList);
        // 记录两侧访问的单词及其到起点/终点的步数
        Map<String, Integer> visitedBegin = new HashMap<>();
        Map<String, Integer> visitedEnd = new HashMap<>();
        visitedBegin.put(beginWord, 0);
        visitedEnd.put(endWord, 0);
        // BFS 队列初始化（从两侧同时出发）
        Queue<String> queueBegin = new LinkedList<>();
        Queue<String> queueEnd = new LinkedList<>();
        queueBegin.offer(beginWord);
        queueEnd.offer(endWord);
        // 主循环：只要两边都未穷尽，就继续搜索
        while (!queueBegin.isEmpty() && !queueEnd.isEmpty()) {
            // 始终从节点数量较少的一侧扩展，减少搜索空间
            if (queueBegin.size() > queueEnd.size()) {
                Queue<String> tmpQ = queueBegin;
                queueBegin = queueEnd;
                queueEnd = tmpQ;
                Map<String, Integer> tmpV = visitedBegin;
                visitedBegin = visitedEnd;
                visitedEnd = tmpV;
            }
            int size = queueBegin.size();
            // 扩展当前层的所有节点
            for (int i = 0; i < size; i++) {
                String cur = queueBegin.poll();
                char[] curChars = cur.toCharArray();
                // 枚举当前单词的每个字母位置
                for (int j = 0; j < curChars.length; j++) {
                    char temp = curChars[j];
                    // 尝试替换为 26 个字母
                    for (char c : chars) {
                        curChars[j] = c;
                        String next = new String(curChars);
                        // 若另一侧已访问该单词，则相遇
                        if (visitedEnd.containsKey(next)) {
                            return visitedBegin.get(cur) + visitedEnd.get(next) + 2;
                        }
                        // 若单词有效且未访问，则加入队列继续搜索
                        if (wordSet.contains(next) && !visitedBegin.containsKey(next)) {
                            visitedBegin.put(next, visitedBegin.get(cur) + 1);
                            queueBegin.offer(next);
                        }
                    }
                    // 恢复原字母，进入下一个位置替换
                    curChars[j] = temp;
                }
            }
        }
        // 若两侧都无法相遇，返回 0
        return 0;
    }

}
