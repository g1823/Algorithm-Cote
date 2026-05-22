package leetCode.difficult;

import java.util.*;

/**
 * @description: 1345. 跳跃游戏 IV
 */
public class MinJumps {

    public static void main(String[] args) {
        int[] arr = new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404};
        System.out.println(new MinJumps().minJumps(arr));
    }

    /**
     * 计算从数组起始位置到达末尾位置的最少跳跃次数
     * <p>
     * 算法详解：
     * <p>
     * 一、问题本质
     * - 在位置 i 时，有三种移动方式：
     * 1. arr[i] == arr[j] 且 i != j：可以直接跳转到任意相同值的位置 j
     * 2. i > 0：向左移动一步到 i-1
     * 3. i < n-1：向右移动一步到 i+1
     * - 每一步操作计为 1，求到达最后位置的最少步数
     * <p>
     * 二、图论建模
     * - 将数组索引看作图的节点
     * - 节点之间可移动则存在边（相同值跳转、左跳、右跳）
     * - 问题转化为：无权图中从节点 0 到节点 n-1 的最短路径
     * - 最短路径 → BFS 层序遍历
     * <p>
     * 三、优化策略
     * 1. 值到索引的映射（空间换时间）
     * - 朴素 BFS 需要 O(n²) 遍历所有相同值对
     * - 使用 HashMap<值, List<索引>> 可快速找到相同值的其他位置
     * - 时间复杂度从 O(n²) 优化到 O(n)
     * <p>
     * 2. 清除已访问值的映射（防止重复）
     * - 第一次处理某值时，将该值的所有索引加入队列
     * - 处理完后清除映射（map.get(arr[cur]).clear()）
     * - 后续不会再遍历该值，节省大量重复操作
     * - 例如：数组中有 1000 个相同值，只需遍历一次
     * <p>
     * 3. visited 数组防重复
     * - 入队时标记 visited = 1，确保每个节点最多入队一次
     * - 防止因环形路径导致的无限循环
     * <p>
     * 四、正确性证明（简要）
     * - BFS 按层展开，第 k 层包含所有距离为 k 的节点
     * - 首次到达目标节点时，对应的层数即为最短距离
     * - visited 机制保证不漏不重
     * <p>
     * 五、复杂度分析
     * - 时间：O(n) — 每个元素最多被访问一次
     * - 空间：O(n) — visited 数组、队列、哈希映射
     * <p>
     * 六、BFS 遍历细节
     * - 外层 while：队列非空时继续
     * - 内层 for：处理当前层所有节点（size = queue.size()）
     * - 每处理完一层，res++（步数 +1）
     * - 目标检测：在处理节点时立即检测（而非入队时）
     */
    public int minJumps(int[] arr) {
        // 【步骤1】构建值到索引的映射
        // 键：数组元素的值
        // 值：该值在数组中出现的所有索引列表
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            // 如果该值还未在 map 中，创建新的列表
            map.putIfAbsent(arr[i], new ArrayList<>());
            // 将当前索引添加到该值对应的列表中
            map.get(arr[i]).add(i);
        }

        // 【步骤2】初始化 BFS
        // visited[i] = 1 表示索引 i 已被访问过
        int[] visited = new int[arr.length];
        // 队列存储待访问的索引
        Queue<Integer> queue = new LinkedList<>();
        // 从索引 0 开始
        queue.add(0);
        // 标记起始位置已访问
        visited[0] = 1;
        // res 记录当前层数（跳跃次数）
        int res = 0;

        // 【步骤3】BFS 层序遍历
        while (!queue.isEmpty()) {
            // 当前层的节点数量
            int size = queue.size();

            // 遍历当前层所有节点
            for (int i = 0; i < size; i++) {
                // 取出队首节点作为当前处理位置
                int cur = queue.poll();

                // 【目标检测】到达末尾位置，直接返回当前步数
                // 此时 res 为从起点到达 cur 的最少步数
                if (cur == arr.length - 1) {
                    return res;
                }

                // 【移动方式1】向左跳一步
                // 条件：不是最左侧位置 且 左侧节点未访问
                // 访问时标记 visited，防止后续重复入队
                if (cur > 0 && visited[cur - 1] == 0) {
                    queue.add(cur - 1);
                    visited[cur - 1] = 1;
                }

                // 【移动方式2】向右跳一步
                // 条件：不是最右侧位置 且 右侧节点未访问
                if (cur < arr.length - 1 && visited[cur + 1] == 0) {
                    queue.add(cur + 1);
                    visited[cur + 1] = 1;
                }

                // 【移动方式3】跳转到所有相同值的其他位置
                // 从映射中获取当前值对应的所有索引
                // 注意：这里也包括 cur 自身，但 cur 已标记 visited，不会重复入队
                for (int j : map.get(arr[cur])) {
                    if (visited[j] == 0) {
                        queue.add(j);
                        visited[j] = 1;
                    }
                }

                // 【关键优化】清除当前值的映射
                // 处理完 cur 后，同值跳转不再需要
                // 防止后续节点重复遍历相同值的索引列表
                // 这一步将相同值跳转的时间复杂度从 O(n²) 降到 O(n)
                map.get(arr[cur]).clear();
            }

            // 【层数递增】处理完当前层，步数 +1
            res++;
        }

        // 【兜底】理论上总能到达，return res 不会执行
        return res;
    }
}
