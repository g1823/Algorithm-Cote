package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: gj
 * @description: 98. 验证二叉搜索树
 */
public class IsValidBST {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(32);
        TreeNode node1 = new TreeNode(26);
        TreeNode node2 = new TreeNode(47);
        TreeNode node3 = new TreeNode(19);
        TreeNode node4 = new TreeNode(56);
        TreeNode node5 = new TreeNode(27);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node3.right = node5;
        node2.right = node4;
//        TreeNode root = new TreeNode(0);
//        root.left = new TreeNode(-1);
        // root.right = new TreeNode(2);
        System.out.println(new IsValidBST().isValidBST(root));
    }

    boolean flag = true;

    int[] t = new int[2];

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        }
        dfs(root);
        return flag;
    }

    public int[] dfs(TreeNode node) {
        if (!flag) {
            return t;
        }
        if (node.left == null && node.right == null) {
            return new int[]{node.val, node.val};
        }
        int[] left = null, right = null;
        if (node.left != null) {
            left = dfs(node.left);
        }
        if (node.right != null) {
            right = dfs(node.right);
        }
        if (left != null && left[1] >= node.val) {
            flag = false;
            return t;
        }
        if (right != null && right[0] <= node.val) {
            flag = false;
            return t;
        }
        int min = node.val, max = node.val;
        if (left != null) {
            min = left[0];
        }
        if (right != null) {
            max = right[1];
        }
        return new int[]{min, max};
    }


    /**
     * 根据官方题解优化：
     * isValidBST()：自下而上，从叶子节点向上，汇总最大值最小值，判断当前节点是否大于左子树的最大值，当前节点是否小于右子树的最小值。
     * 但是，isValidBST()需要全局变量标识。
     * isValidBST2()自上而下，根据当前节点划分左右子树的取值范围，往下遍历时维护左右子树的取值范围。
     *
     * @param root 根节点
     * @return 是否正确
     */
    public boolean isValidBST2(TreeNode root) {
        // 初始上下界为 null
        return dfs2(root, null, null);
    }

    private boolean dfs2(TreeNode node, Integer lower, Integer upper) {
        if (node == null) {
            return true;
        }
        // 检查当前节点是否在 (lower, upper) 范围内
        if (lower != null && node.val <= lower) {
            return false;
        }
        if (upper != null && node.val >= upper) {
            return false;
        }
        // 递归检查左子树（最大值不超过 node.val）和右子树（最小值不小于 node.val）
        return dfs2(node.left, lower, node.val) && dfs2(node.right, node.val, upper);
    }


    /**
     * 二叉搜索树，中序遍历的话，一定是一个递增的数列。可以中序遍历，然后只要当前节点小于上一个节点，就返回false。
     *
     * @param root 根结点
     * @return 是否正确
     */
    public boolean isValidBST3(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        double inorder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }

}
