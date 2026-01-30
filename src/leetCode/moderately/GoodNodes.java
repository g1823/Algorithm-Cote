package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 1448. 统计二叉树中好节点的数目
 */
public class GoodNodes {
    private int count = 0;

    public int goodNodes(TreeNode root) {
        count = 0;
        dfs(root, root.val);
        return count;
    }

    private void dfs(TreeNode node, int max) {
        if (node == null) {
            return;
        }
        if (node.val >= max) {
            count++;
            max = node.val;
        }
        dfs(node.left, max);
        dfs(node.right, max);
    }
}
