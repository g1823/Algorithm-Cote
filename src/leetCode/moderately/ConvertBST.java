package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 538. 把二叉搜索树转换为累加树
 */
public class ConvertBST {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(6);
        TreeNode node3 = new TreeNode(0);
        TreeNode node4 = new TreeNode(2);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(7);
        TreeNode node7 = new TreeNode(3);
        TreeNode node8 = new TreeNode(8);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node4.right = node7;
        node6.right = node8;
        convertBST2(root);
    }

    /**
     * 将二叉搜索树（BST）转换为累加树（Greater Tree）。
     * 累加树的定义是：每个节点的值等于原始节点值加上所有大于它的节点值之和。
     * <p>
     * 算法分两遍遍历实现：
     * 1. dfs()：
     * - 自底向上递归计算每个节点的子树总和（左 + 右 + 当前节点）。
     * - 同时将每个节点的值更新为：当前节点值 + 其右子树的和（即加上比它大的所有子节点值）。
     * - 这一遍处理完后，只有一路向右的路径上节点值是正确的，其余节点值仍需调整。
     * <p>
     * 2. dfs2()：
     * - 用于补充更新所有非右路径上节点的值。
     * - 传入参数 sum 表示除当前节点子树外、所有比当前节点大的节点值之和。
     * - 递归方式为：右 → 根 → 左，符合 BST 的降序遍历顺序。
     * - 更新规则为：当前节点值 += sum，然后将新的 sum 传递给左子树。
     *
     * @param root 二叉搜索树根节点
     * @return 转换后的累加树根节点
     */
    public static TreeNode convertBST(TreeNode root) {
        dfs(root);
        dfs2(root, 0);
        return root;
    }

    /**
     * 第一次 DFS：计算每个节点的右子树和并累加到当前节点上。
     *
     * @param node 当前节点
     * @return 当前节点的整棵子树和（用于上层节点更新）
     */
    private static int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        node.val += right;
        return node.val + left;
    }

    /**
     * 第二次 DFS：传递右边累加值，继续更新剩余节点。
     *
     * @param node 当前节点
     * @param sum  所有比当前节点大的外部节点的累加值
     */
    private static void dfs2(TreeNode node, int sum) {
        if (node == null) {
            return;
        }
        int original = node.val;
        node.val += sum;
        // 向右传递原本累加值，因为右子树任意节点都比当前节点大，所以需要减去当前节点
        dfs2(node.right, node.val - original);
        // 向左传递当前更新后的值
        dfs2(node.left, node.val);
    }


    /**
     * 方法1需要处理两次，然而两次是可以合一的
     * <p>
     * 采用递归的方式进行中序逆序遍历（右 -> 根 -> 左）
     * 递归思路：
     * - 每个节点更新为其原值加上所有比它大的节点值之和；
     * - 遍历右子树时不断累加较大的值；
     * - 左子树在递归时接收已累加的结果。
     * <p>
     * 关键点：
     * - 当右子树为空时，将累加和 sum 加入当前节点；
     * - 然后将当前节点更新后的值作为新的累加和传给左子树。
     * <p>
     * 比如：对于任意节点
     * - 先遍历右子树，从最右侧节点开始，递归返回累加值 sum（当遍历到某个节点其左子树为空时，则会返回sum值，并将sum值累加到当前节点上，这样会使的外部传来的累加值（所有比当前节点大的节点值之和）累加上）
     * - 当前节点将返回的 sum 加到自己的值上，实现“加上所有比它大的节点值”；
     * - 然后再将当前节点更新后的值作为新的 sum 传递给左子树，
     * - 左子树的每个节点会继续递归更新，以此实现整棵树从大到小的累加过程。
     *
     * @param root BST的根节点
     * @return 转换后的累加树根节点
     */
    public static TreeNode convertBST2(TreeNode root) {
        newDfs(root, 0);
        return root;
    }

    /**
     * 递归遍历函数：将BST转换为累加树。
     *
     * @param node 当前处理的节点
     * @param sum  当前节点右侧所有节点（比当前节点大的节点）累加和
     * @return 当前子树中最左节点的累加值，也就是：每次递归返回的是“包含当前子树的最大累加值”
     */
    public static int newDfs(TreeNode node, int sum) {
        if (node == null) {
            // 当前为空节点，返回累加和，用于上一层的左子树处理
            return sum;
        }

        // 处理右子树，先获取右子树累加值（大于当前节点的所有值）
        int right = newDfs(node.right, sum);

        // 更新当前节点值，累加上右子树的和
        node.val += right;

        // 处理左子树，传入当前节点更新后的值，作为新的累加和
        return newDfs(node.left, node.val);
    }
}
