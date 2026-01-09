package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: gj
 * @description: 1161. 最大层内元素和
 */
public class MaxLevelSum {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        TreeNode node1 = new TreeNode(7);
        TreeNode node2 = new TreeNode(0);
        TreeNode node3 = new TreeNode(7);
        TreeNode node4 = new TreeNode(-8);
        node.left = node1;
        node.right = node2;
        node1.left = node3;
        node1.right = node4;
        System.out.println(new MaxLevelSum().maxLevelSum(node));
    }

    /**
     * 直接广度优先遍历即可
     */
    public int maxLevelSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        long maxSum = root.val;
        int maxLevel = 1;
        int level = 1;
        // 层序遍历
        while (!queue.isEmpty()) {
            // 当前层节点数
            int size = queue.size();
            long sum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            if (sum > maxSum) {
                maxSum = sum;
                maxLevel = level;
            }
            level++;
        }
        return maxLevel;
    }
}
