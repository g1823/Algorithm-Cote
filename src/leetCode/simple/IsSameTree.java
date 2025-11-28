package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 100. 相同的树
 */
public class IsSameTree {
    /**
     * 直接深度优先同时遍历两棵树，深度优先保证当前节点处于两棵树的位置相同。
     * 接下来对比值：
     * 1、两个节点都为Null，说明同时到达叶子节点的下一节点（null），返回true
     * 2、两个节点都非Null，且值相同，则可以继续对比叶子节点，则继续判断子节点（左和右）是否相同
     * 3、两个节点都非Null，且值不同，则返回false（到当前节点直接不同了，不需要继续遍历叶子节点了，直接返回false）
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == q) {
            return true;
        }
        return dfs(p, q);
    }

    public boolean dfs(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val == q.val) {
            return dfs(p.left, q.left) && dfs(p.right, q.right);
        } else {
            return false;
        }
    }
}
