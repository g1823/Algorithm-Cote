package leetCode.moderately;

import leetCode.help.TreeNode;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 337. 打家劫舍 III
 */
public class Rob3 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        root.left = node1;
        root.right = node2;
        node1.right = node3;
        System.out.println(new Rob3().rob2(root));
    }

    int result = 0;

    /**
     * 直接深度优先遍历，然后把深度优先每一条路径采用动态规划思想获取最大值，但是无法考虑同时选择两条路径上的最优解
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        List<Integer> record = new ArrayList<>();
        dfs(root, record, 0);
        return result;
    }

    public void dfs(TreeNode node, List<Integer> record, int index) {
        if (node == null) return;
        int val;
        if (index <= 1) {
            val = index == 0 ? node.val : Math.max(node.val, record.get(0));
        } else {
            val = Math.max(node.val + record.get(index - 2), record.get(index - 1));
        }
        result = Math.max(val, result);
        record.add(index, val);
        dfs(node.left, record, index + 1);
        dfs(node.right, record, index + 1);
        record.remove(index);
    }


    /**
     * 采用递归的特性，每个方法会保存当前节点信息，从叶子节点往前遍历
     * 记录采用当前节点的值(当前节点值 + 不采用当前节点左节点的最优解 + 不采用当前节点右节点的最优解)
     * 以及不采用当前节点的最优解，以下四种情况中最大值：
     * ① 不采用当前节点左节点的最优解 + 不采用当前节点右节点的最优解
     * ② 采用当前节点左节点的最优解 + 采用当前节点右节点的最优解
     * ① 不采用当前节点左节点的最优解 + 采用当前节点右节点的最优解
     * ③ 采用当前节点左节点的最优解 + 不采用当前节点右节点的最优解
     * @param root
     * @return
     */
    public int rob2(TreeNode root) {
        int[] leftResult = dfs2(root.left);
        int[] rightResult = dfs2(root.right);
        return Math.max(root.val + leftResult[1] + rightResult[1],
                Math.max(
                        Math.max(leftResult[0] + rightResult[1], leftResult[1] + rightResult[0]),
                        Math.max(leftResult[0] + rightResult[0], leftResult[1] + rightResult[1])
                ));
    }

    public int[] dfs2(TreeNode node) {
        if (node == null) return new int[]{0, 0};
        int[] leftResult = dfs2(node.left);
        int[] rightResult = dfs2(node.right);
        return new int[]{node.val + leftResult[1] + rightResult[1],
                Math.max(
                        Math.max(leftResult[0] + rightResult[1], leftResult[1] + rightResult[0]),
                        Math.max(leftResult[0] + rightResult[0], leftResult[1] + rightResult[1])
                )};
    }
}
