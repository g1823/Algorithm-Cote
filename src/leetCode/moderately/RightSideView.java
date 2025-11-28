package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 199. 二叉树的右视图
 */
public class RightSideView {

    int maxLevel = -1;

    /**
     * 思路：
     * 1. 使用深度优先搜索（DFS）遍历二叉树，但优先访问右子树，再访问左子树。
     * 这样在每一层第一次遇到的节点就是该层最右边的节点。
     * 2. 用 maxLevel 记录当前已经访问过的最大层数（从 0 开始计数）。
     * 3. 在遍历过程中，当当前层数大于 maxLevel 时，说明这是该层第一个访问到的节点，
     * 将它加入结果列表，并更新 maxLevel。
     * 4. 递归时，先递归右子树，再递归左子树，以确保优先选取右边的节点。
     * 时间复杂度：O(n)，n 为节点总数，每个节点仅访问一次。
     * 空间复杂度：O(h)，h 为树的高度，递归调用栈所需空间。
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    public void dfs(TreeNode node, int level, List<Integer> res) {
        if (node == null) {
            return;
        }
        // 当前节点深度大于最大深度，可以被看到
        if (level > maxLevel) {
            res.add(node.val);
            maxLevel = level;
        }
        dfs(node.right, level + 1, res);
        dfs(node.left, level + 1, res);
    }
}
