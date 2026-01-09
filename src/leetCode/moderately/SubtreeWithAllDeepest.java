package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 865. 具有所有最深节点的最小子树
 */
public class SubtreeWithAllDeepest {

    /**
     * 本质为树形动态规划求多节点最近公共祖先（LCA）问题
     * - 思考过程与解题分析：
     * -
     * - 1. 本题的本质理解：
     * -    - 题目要求找到包含所有最深节点的最小子树。
     * -    - 换句话说，本质上是：**返回包含指定节点集合的最小公共子树**。
     * -      在本题中，指定节点集合就是“所有最深的叶子节点”。
     * -    - 因此，可以把问题抽象为“多节点最近公共祖先（LCA）问题”，
     * -      只是节点集合是动态计算出来的最深节点。
     * -
     * - 2. 初始思路：
     * -    - 第一步：DFS 找出最深节点的深度或节点
     * -      （之前误以为需要返回节点值或路径标记）
     * -    - 第二步：自底向上标记路径上节点
     * -    - 第三步：自顶向下判断左右子树是否都包含最深节点，
     * -      确定最小子树
     * -
     * - 3. 遇到的问题与理解纠正：
     * -    - 最初尝试在 DFS 中用 node.val 累加作为深度，这是错误的。
     * -      深度应该是**层级深度**，即每向下一层 +1。
     * -    - 多次 DFS（求深度 + 标记路径 + 判断最小子树）可以**合并为一次 DFS**，
     * -      通过递归返回子树最大深度与对应最小子树节点，实现自底向上判断。
     * -
     * - 4. 抽象思路优化：
     * -    - 对每个节点递归计算左右子树：
     * -        left = dfs(node.left)
     * -        right = dfs(node.right)
     * -    - 返回值包含：
     * -        - 当前子树的最大深度
     * -        - 当前子树包含所有最深节点的最小子树
     * -    - 递归汇总逻辑：
     * -        1. 如果 left.depth == right.depth：
     * -            当前节点就是最小子树
     * -        2. 如果 left.depth > right.depth：
     * -            最大深度只在左子树 → 返回左子树对应最小子树
     * -        3. 如果 right.depth > left.depth：
     * -            最大深度只在右子树 → 返回右子树对应最小子树
     * -    - 通过递归自底向上，自然筛掉小于最大深度的分支。
     * -
     * - 5. 算法特点：
     * -    - 一次 DFS 即可完成全部逻辑，无需额外标记或多次遍历
     * -    - 没有全局变量，递归返回信息自包含
     * -    - 本质是**树形动态规划（Tree DP）**：
     * -        每个节点把子问题信息（最大深度 + 对应最小子树）返回给父节点
     * -        父节点做决策 → 最终根节点返回整个树的答案
     * -
     * - @param root 二叉树根节点
     * - @return 包含所有最深节点的最小子树的根节点
     */
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        return dfs(root, 0).node;
    }

    /**
     * Info 类：存储 DFS 返回信息
     * - depth：当前子树的最大深度（从根开始计算）
     * - node：当前子树包含所有最深节点的最小子树根节点
     */
    class Info {
        int depth;
        TreeNode node;

        public Info(int depth, TreeNode node) {
            this.depth = depth;
            this.node = node;
        }
    }

    /**
     * DFS 遍历函数
     *
     * @param node  当前节点
     * @param depth 当前节点层级深度
     * @return Info 对象，包含当前子树最大深度及对应最小子树根节点
     */
    public Info dfs(TreeNode node, int depth) {
        // 空节点返回当前深度和空子树
        if (node == null) {
            return new Info(depth, null);
        }

        // 递归遍历左右子树，层级深度 +1
        Info left = dfs(node.left, depth + 1);
        Info right = dfs(node.right, depth + 1);

        // 核心逻辑：
        // 1. 左右子树最大深度相等 → 当前节点是最小子树
        // 2. 左深度大 → 返回左子树对应最小子树
        // 3. 右深度大 → 返回右子树对应最小子树
        if (left.depth == right.depth) {
            return new Info(left.depth, node);
        }
        return left.depth > right.depth ? left : right;
    }


}
