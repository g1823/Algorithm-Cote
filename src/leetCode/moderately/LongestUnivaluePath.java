package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @description: 687. 最长同值路径
 */
public class LongestUnivaluePath {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(5);
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(5);
        TreeNode node3 = new TreeNode(1);
        TreeNode node4 = new TreeNode(1);
        TreeNode node5 = new TreeNode(5);
        node.left = node1;
        node.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.right = node5;
        System.out.println(new LongestUnivaluePath().longestUnivaluePath(node));
    }

    int res = 1;

    /**
     * 动态规划（树形dp）
     */
    public int longestUnivaluePath(TreeNode root) {
        dfs(root);
        return res - 1;
    }

    /**
     * 注意，返回时只能返回单侧路径的最长，不能返回合并左右叶子后的最长，一旦采取了合并左右子树，那么就不能和父级再合并了
     */
    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int l = dfs(node.left);
        int r = dfs(node.right);
        // 至少长度为1，只有当前节点时为1
        int t = 1;
        // 先计算从当前节点向下的单侧最大延伸
        if (node.left != null && node.val == node.left.val) {
            t = Math.max(t, l + 1);
        }
        if (node.right != null && node.val == node.right.val) {
            t = Math.max(t, r + 1);
        }

        // 更新全局答案：经过当前节点的路径（可左右合并）
        if (node.left != null && node.right != null
                && node.left.val == node.right.val
                && node.val == node.left.val) {
            res = Math.max(res, l + r + 1);
        }

        // 实际上 t 已经代表单侧最大，更新 res 为 t 也可以
        res = Math.max(res, t);
        return t;
    }
}
