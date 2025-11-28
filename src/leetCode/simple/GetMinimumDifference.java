package leetCode.simple;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 530. 二叉搜索树的最小绝对差
 */
public class GetMinimumDifference {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(236);
        TreeNode node1 = new TreeNode(104);
        TreeNode node2 = new TreeNode(701);
        TreeNode node3 = new TreeNode(227);
        TreeNode node4 = new TreeNode(911);
        node.left = node1;
        node.right = node2;
        node1.right = node3;
        node2.right = node4;


        TreeNode n1 = new TreeNode(100000);
        TreeNode n2 = new TreeNode(0);
        n1.left = n2;
        System.out.println(new GetMinimumDifference().getMinimumDifference2(n1));
    }

    /**
     * 错误
     * 开始思路，因为是二叉搜索树，因此认为父节点的值一定是最接近两个子节点的值的
     * 然而这个想法是错的，因为当前节点的右节点和自己的差值可能比当前节点的右节点和当前节点的父节点差值大
     */
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.min(dfs(root.left, root.val), dfs(root.right, root.val));
    }

    public int dfs(TreeNode node, int parentValue) {
        if (node == null) {
            return Integer.MAX_VALUE;
        }
        int cur = Math.abs(node.val - parentValue);
        int left = dfs(node.left, node.val);
        int right = dfs(node.right, node.val);
        return Math.min(Math.min(left, right), cur);
    }

    /**
     * 直接中序遍历，得到的就是一个递增的数组，依次找到差值最小的即可
     */
    public int getMinimumDifference2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        List<Integer> ints = new ArrayList<>();
        inorder(root, ints);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < ints.size(); i++) {
            int cur = ints.get(i) - ints.get(i - 1);
            if (cur < min) {
                min = cur;
            }
        }
        return min;
    }

    public void inorder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inorder(node.left, list);
        list.add(node.val);
        inorder(node.right, list);
    }

    /**
     * 依旧中序遍历，但是根据题目要求，0 <= Node.val <= 10^5，因此可以特殊处理，默认第一个元素为-1，当找到中序遍历第一个节点时，也就是最左侧叶子节点，替换-1即可
     */
    public int getMinimumDifference3(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int[] ints = {100000, -1};
        inorder(root, ints);
        return ints[0];
    }

    public void inorder(TreeNode node, int[] arr) {
        if (node == null) {
            return;
        }
        inorder(node.left, arr);
        // 第一个节点，替换原来的-1
        if (arr[1] != -1) {
            arr[0] = Math.min(arr[0], node.val - arr[1]);
        }
        arr[1] = node.val;
        inorder(node.right, arr);
    }


}
