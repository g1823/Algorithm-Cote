package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 129. 求根节点到叶节点数字之和
 */
public class SumNumbers {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(4);
        TreeNode node1 = new TreeNode(9);
        TreeNode node2 = new TreeNode(0);
        TreeNode node3 = new TreeNode(5);
        TreeNode node4 = new TreeNode(1);
        node.left = node1;
        node.right = node2;
        node1.left = node3;
        node1.right = node4;
        SumNumbers sumNumbers = new SumNumbers();
        System.out.println(sumNumbers.sumNumbers(node));
    }

    /**
     * 错误
     * 因为每个结点的数都是0-9，因此如果知道某个子节点路径和，那么就可以根据该和取对数，得到深度，因而得到当前节点处于第几位。
     * 问题：某个节点其不止是简单的只会存在两个数字中（左右子节点），因为其左右子节点还会存在子节点，因此该数字会被使用多次，而不是简单的两次
     */
    public int sumNumbers(TreeNode root) {
        return dfs(root);
    }

    public int dfs(TreeNode node) {
        if (node == null) {
            return -1;
        }
        if (node.left == null && node.right == null) {
            return node.val;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        int current = 0;
        if (left != -1) {
            current += getDigits(left) * 10 * node.val;
        }
        if (right != -1) {
            current += getDigits(right) * 10 * node.val;
        }
        return current + left + right;
    }

    public static int getDigits(int num) {
        if (num == 0) {
            return 1;
        }
        return (int) Math.log10(num) + 1;
    }


    /**
     * 题目：求根节点到叶子节点数字之和（全局变量版）
     * 思路分析：
     * 1. 必须从根节点向叶子节点方向构造数字：
     * - 因为一个节点可能属于多条不同路径，无法从叶子节点反推出该节点参与了几条路径。
     * - 路径的方向是唯一确定的（根 -> 叶），因此递归时可以沿路径顺序构造出每条完整数字。
     * 2. 路径数字的构造：
     * - 每向下一层，相当于在当前数字的右侧追加一位。
     * - 因此可以使用公式：currentSum = currentSum * 10 + node.val。
     * - 这样构造出的数字与路径顺序一致，不需要额外逆向或回溯。
     * 3. 回溯处理：
     * - 这里不需要手动删除或回退节点值，因为 currentSum 是递归参数，属于局部变量。
     * - 每次递归返回后，上一层的 currentSum 会自动恢复。
     * 4. 终止条件：
     * - 当遍历到叶子节点时，说明构造出了一条完整路径，直接将该路径数字累加到全局 sum 中。
     * 时间复杂度：O(N)，每个节点只访问一次。
     * 空间复杂度：O(H)，H 为树的高度（递归栈）。
     */

    public int sumNumbers2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs2(root, 0);
        return sum;
    }

    // 全局变量，用于累计所有根到叶子的路径数字和
    int sum = 0;

    public void dfs2(TreeNode node, int currentSum) {
        // 递归终止：空节点直接返回
        if (node == null) {
            return;
        }
        // 构造当前路径的数字
        // 每往下一层，相当于把之前的路径数字 *10，再加上当前节点的值
        currentSum = currentSum * 10 + node.val;
        // 如果到达叶子节点，说明完整构造出一条路径数字
        if (node.left == null && node.right == null) {
            sum += currentSum;
            // 返回上一层
            return;
        }
        // 继续向左子树递归
        if (node.left != null) {
            dfs2(node.left, currentSum);
        }

        // 继续向右子树递归
        if (node.right != null) {
            dfs2(node.right, currentSum);
        }
    }


    /**
     * sumNumbers2优化版本：去除全局变量，一直到叶子节点才返回从根到叶子节点构造的数字，然后累加即可。
     */
    public int sumNumbers3(TreeNode root) {
        return dfs3(root, 0);
    }

    private int dfs3(TreeNode node, int currentSum) {
        if (node == null) {
            return 0;
        }

        // 构造当前路径的数字
        currentSum = currentSum * 10 + node.val;

        // 到达叶子节点，返回该路径形成的数字
        if (node.left == null && node.right == null) {
            return currentSum;
        }

        // 递归计算左右子树路径数字之和
        int leftSum = dfs3(node.left, currentSum);
        int rightSum = dfs3(node.right, currentSum);

        // 汇总结果
        return leftSum + rightSum;
    }
}
