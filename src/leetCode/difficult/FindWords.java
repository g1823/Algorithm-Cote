package leetCode.difficult;


import java.util.*;

/**
 * @author: gj
 * @description: 212. 单词搜索 II
 */
public class FindWords {

    public static void main(String[] args) {
        char[][] board = {
                {'a', 'b', 'c'},
                {'a', 'e', 'd'},
                {'a', 'f', 'g'}};
        String[] words = {"eaafgdcba"};
        System.out.println(new FindWords().findWords(board, words));
    }

    /**
     * 解法一：朴素回溯解法（按单词逐个搜索）
     * 核心思路：
     * 1. 先遍历 board，将每个字符在 board 中出现的位置全部记录下来。
     * -  这样可以在处理某个单词时，快速定位它的首字符是否存在，以及所有可能的起点。
     * 2. 对 words 中的每一个单词，分别进行一次回溯搜索：
     * - 如果首字符在 board 中不存在，直接跳过该单词
     * - 如果存在，则从每一个首字符位置出发，尝试通过 DFS 在 board 中拼出该单词
     * 3. 在 DFS 过程中：
     * - 使用 visited 数组记录已经访问过的格子，避免重复使用同一个字符
     * - 每一层 DFS 对应匹配单词中的一个字符
     * - 只要有一条路径能够完整匹配该单词，就认为该单词存在
     * 特点与问题：
     * - 这是 LeetCode 79「单词搜索」的直接扩展思路
     * - 对每一个单词都会独立进行一次 DFS
     * - 当 words 数量较多、且存在大量公共前缀时，会产生大量重复搜索，容易超时
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        /**
         * key   : board 中出现的字符
         * value : 该字符在 board 中出现的所有位置（用一维索引 i * n + j 表示）
         *
         * 该结构用于快速判断：
         * - 某个单词的首字符是否存在于 board
         * - 以及该首字符所有可能的起始坐标
         */
        Map<Character, List<Integer>> map = new HashMap<>(32);
        int m = board.length;
        int n = board[0].length;
        // 预处理 board，将字符出现位置全部记录下来
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = board[i][j];
                map.computeIfAbsent(c, k -> new ArrayList<>()).add(i * n + j);
            }
        }

        // 逐个处理每一个单词
        for (String word : words) {
            char[] chars = word.toCharArray();
            // 如果首字符在 board 中不存在，直接跳过
            if (!map.containsKey(chars[0])) {
                continue;
            }
            // 从所有可能的首字符位置出发进行 DFS
            for (Integer index : map.get(word.charAt(0))) {
                boolean[][] visited = new boolean[m][n];
                int i = index / n;
                int j = index % n;
                visited[i][j] = true;
                /**
                 * 从首字符开始，匹配 word 的第 1 个字符（index = 1）
                 * 只要有一次 DFS 成功，就认为该单词存在
                 */
                if (dfs(board, word, i, j, 1, visited)) {
                    res.add(word);
                    break;
                }
            }
        }
        return res;
    }

    /**
     * DFS 回溯函数，用于判断从当前位置 (i, j) 出发，
     * 是否能够匹配 word 中从 index 开始的剩余字符
     *
     * @param board   字符网格
     * @param word    当前要匹配的单词
     * @param i       当前所在行
     * @param j       当前所在列
     * @param index   当前要匹配的字符下标
     * @param visited 记录当前 DFS 路径中已经访问过的格子
     * @return 是否能够从当前位置成功匹配到单词结尾
     */
    private boolean dfs(char[][] board, String word, int i, int j, int index, boolean[][] visited) {
        /**
         * 当 index 等于单词长度，说明之前的字符已经全部匹配完成
         * 当前路径构成了一个完整的单词
         */
        if (index == word.length()) {
            return true;
        }
        boolean res = false;
        // 向上搜索
        if (i > 0 && !visited[i - 1][j] && board[i - 1][j] == word.charAt(index)) {
            visited[i - 1][j] = true;
            res = dfs(board, word, i - 1, j, index + 1, visited);
            visited[i - 1][j] = false;
        }
        // 向下搜索（只有在上一个方向失败时才继续）
        if (!res && i < board.length - 1 && !visited[i + 1][j] && board[i + 1][j] == word.charAt(index)) {
            visited[i + 1][j] = true;
            res = dfs(board, word, i + 1, j, index + 1, visited);
            visited[i + 1][j] = false;
        }
        // 向左搜索
        if (!res && j > 0 && !visited[i][j - 1] && board[i][j - 1] == word.charAt(index)) {
            visited[i][j - 1] = true;
            res = dfs(board, word, i, j - 1, index + 1, visited);
            visited[i][j - 1] = false;
        }
        // 向右搜索
        if (!res && j < board[0].length - 1 && !visited[i][j + 1] && board[i][j + 1] == word.charAt(index)) {
            visited[i][j + 1] = true;
            res = dfs(board, word, i, j + 1, index + 1, visited);
            visited[i][j + 1] = false;
        }
        return res;
    }


    class Solution2 {

        /**
         * 前缀树（Trie）
         * 设计目的：
         * 1、用于高效判断：
         * - 当前字符路径是否仍然是某些单词的前缀
         * - 当前字符路径是否已经完整构成一个单词
         * 2、替代解法一中：
         * - 对每个 word 单独 DFS
         * - 重复在 board 上进行大量相同路径的搜索
         * 核心思想：
         * - Trie 的结构天然对应“前缀约束”
         * - DFS 在 board 上扩展路径的同时，
         * 利用 Trie 实时剪枝无效路径
         */
        class Trie {
            /**
             * 子节点数组
             * 下标 0~25 分别对应字符 'a' ~ 'z'
             */
            Trie[] children = new Trie[26];
            //标记：当前节点是否为某个单词的结尾
            boolean isEnd = false;

            public Trie() {
            }

            /**
             * 向前缀树中插入一个单词
             * <p>
             * 插入过程本质：
             * - 将单词拆分为字符序列
             * - 沿着前缀树逐层向下构建
             * - 最后一个字符节点标记为 isEnd
             */
            public void insert(String word) {
                char[] chars = word.toCharArray();
                Trie node = this;
                for (char aChar : chars) {
                    if (node.children[aChar - 'a'] == null) {
                        node.children[aChar - 'a'] = new Trie();
                    }
                    node = node.children[aChar - 'a'];
                }
                node.isEnd = true;
            }
        }

        /**
         * 解法二：Trie + DFS（最优解法）
         * 相比解法一的核心变化：
         * 【解法一】
         * - 外层遍历 words
         * - 对每个 word 在 board 上单独做 DFS
         * - 问题：
         * 1、多个 word 会在 board 上重复走相同的路径
         * 2、路径是否“值得继续”只能等字符不匹配时才知道
         * 【解法二】
         * - 先将所有 words 构建成一棵 Trie
         * - 外层遍历 board 的每一个格子
         * - 从 board 出发做 DFS，同时在 Trie 中同步向下走
         * <p>
         * 为什么是“遍历 board，而不是遍历 Trie”？
         * 1、路径的物理约束来自 board
         * - 相邻
         * - 不能重复使用格子
         * - 搜索空间本质由 board 决定
         * 2、Trie 只用于回答两个问题：
         * - 当前路径是否仍是某些单词的前缀？
         * - 当前路径是否已经构成一个完整单词？
         * 3、DFS 的主导者是 board：
         * - board 决定能走哪些方向
         * - Trie 决定“这条路值不值得继续走”
         * 4、比如：
         * - 按Trie遍历，相当于可以优化的是多个前缀相同的单词可以同时遍历，可以优化：abcaaa、abcbbb、abcccc...等前缀相同的情况
         * - 然而，当数据为aaaaaa、baaaaa、caaaaa、daaaaa、eaaaaa、faaaaa...这种时，前缀完全不相同，相当于无优化，退化为了解法一
         * - 而按board遍历，相当于可以优化的是多个相邻的单词可以同时遍历，可以优化：abcd、abef、abgh、abij...等相邻的单词
         * 核心剪枝点：
         * - 一旦当前字符在 Trie 中不存在对应子节点
         * -  说明：以当前路径为前缀的所有单词都不可能匹配
         * -  立即终止 DFS
         * 时间复杂度本质优化点：
         * - 每条 board 路径只会被遍历一次
         * - Trie 将“无效前缀”提前剪掉
         */
        public List<String> findWords(char[][] board, String[] words) {
            // 构建前缀树，将所有单词的前缀信息集中存储
            Trie trie = new Trie();
            for (String word : words) {
                trie.insert(word);
            }
            // 使用 Set 存储结果，避免同一个单词从不同路径被重复加入
            Set<String> res = new HashSet<>();
            // 从每个格子出发尝试作为单词起始位置
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    // StringBuilder 用于维护当前 DFS 路径形成的字符串
                    StringBuilder sb = new StringBuilder();
                    // visited 数组用于保证：一个 DFS 路径中同一个格子只使用一次
                    boolean[][] visited = new boolean[board.length][board[0].length];
                    dfs(board, trie, i, j, sb, visited, res);
                }
            }
            return new ArrayList<>(res);
        }

        private void dfs(char[][] board, Trie trie, int i, int j, StringBuilder sb, boolean[][] visited, Set<String> res) {
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
                return;
            }
            if (visited[i][j]) {
                return;
            }
            char c = board[i][j];
            // 前缀树不存在该字符
            Trie child = trie.children[c - 'a'];
            if (child == null) {
                return;
            }
            sb.append(c);
            visited[i][j] = true;
            if (child.isEnd) {
                res.add(sb.toString());
            }
            // 上
            dfs(board, child, i - 1, j, sb, visited, res);
            // 下
            dfs(board, child, i + 1, j, sb, visited, res);
            // 左
            dfs(board, child, i, j - 1, sb, visited, res);
            // 右
            dfs(board, child, i, j + 1, sb, visited, res);
            visited[i][j] = false;
            sb.deleteCharAt(sb.length() - 1);
        }

    }


    class Solution3 {

        class Trie {
            Trie[] children = new Trie[26];
            boolean isEnd;
            String word; // 直接保存完整单词
        }

        /**
         * 解法三：Trie + DFS（在解法二基础上的进一步优化）
         * 相较于解法二的核心优化点总结：
         * 一、避免 StringBuilder 的频繁构造与回溯
         * 解法二中：
         * - 每一条 DFS 路径都需要维护 StringBuilder
         * - 每次进入 / 回溯都要 append / delete
         * 解法三中：
         * - 在 Trie 的 isEnd 节点直接保存完整单词 word
         * - DFS 过程中不再拼接字符串
         * - 一旦命中 isEnd，直接取出 word
         * => 减少了字符串操作带来的额外时间和空间开销
         * 二、结果去重从“外部 Set”下沉到 Trie 内部
         * 解法二中：
         * - 使用 Set<String> 保存结果
         * - 同一个单词可能被多次搜索命中
         * - 依赖 Set 在最终阶段去重
         * 解法三中：
         * - 命中单词后，直接将 Trie 节点的 isEnd 置为 false
         * - 从源头保证该单词只会被加入一次
         * => 减少了无意义的 DFS 分支继续搜索同一个单词
         * 三、Trie 节点语义进一步增强
         * 解法二：
         * - Trie 只负责“前缀是否存在”和“是否是单词结尾”
         * 解法三：
         * - Trie 节点不仅表示结构
         * - 同时承载“完整单词信息”
         * => Trie 从“辅助剪枝结构”升级为“结果直接来源”
         * 四、整体搜索模型未改变，但常数级性能显著优化
         * - 仍然是：遍历 board + DFS + Trie 剪枝
         * - 仍然由 board 决定搜索空间
         * - 优化点集中在：
         * 1）字符串处理
         * 2）结果去重策略
         * 3）无效 DFS 分支的提前终止
         */
        public List<String> findWords(char[][] board, String[] words) {
            // 构建 Trie
            Trie root = new Trie();
            for (String word : words) {
                insert(root, word);
            }
            List<String> res = new ArrayList<>();
            int m = board.length;
            int n = board[0].length;
            // visited 数组在整个 DFS 过程中复用
            boolean[][] visited = new boolean[m][n];
            // 从 board 的每一个格子作为起点进行 DFS
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dfs(board, i, j, root, visited, res);
                }
            }

            return res;
        }

        /**
         * 向 Trie 中插入单词* 与解法二不同的是：
         * 在单词结尾节点直接保存完整单词
         */
        private void insert(Trie root, String word) {
            Trie node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new Trie();
                }
                node = node.children[idx];
            }
            node.isEnd = true;
            node.word = word;
        }

        private void dfs(char[][] board, int i, int j, Trie node, boolean[][] visited, List<String> res) {
            // 边界检查
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
                return;
            }
            // 当前格子已在本次路径中使用
            if (visited[i][j]) {
                return;
            }
            char c = board[i][j];
            // Trie 前缀剪枝
            Trie child = node.children[c - 'a'];
            if (child == null) {
                return;
            }
            visited[i][j] = true;
            // 命中一个完整单词
            if (child.isEnd) {
                res.add(child.word);
                // 将 isEnd 置为 false，避免同一单词被重复加入
                // 同时减少后续 DFS 的无效搜索
                child.isEnd = false;
            }
            // 继续向四个方向扩展搜索
            dfs(board, i - 1, j, child, visited, res);
            dfs(board, i + 1, j, child, visited, res);
            dfs(board, i, j - 1, child, visited, res);
            dfs(board, i, j + 1, child, visited, res);
            // 回溯，恢复访问状态
            visited[i][j] = false;
        }
    }
}
