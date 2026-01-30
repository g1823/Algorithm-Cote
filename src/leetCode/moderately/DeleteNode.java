package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 450. 删除二叉搜索树中的节点
 */
public class DeleteNode {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(6);
        TreeNode node3 = new TreeNode(8);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(7);
        root.left = node1;
        root.right = node5;
        node1.right = node4;
        node5.left = node2;
        node5.right = node3;
        TreeNode treeNode = new DeleteNode().deleteNode(root, 5);
        System.out.println(treeNode);
    }

    /**
     * - 删除二叉搜索树中的指定节点（BST Delete）
     * - 【最初思路】
     * - 1. 利用 BST 的性质递归查找目标节点：
     * -    - key < node.val → 去左子树
     * -    - key > node.val → 去右子树
     * - 2. 找到目标节点后，分三种情况处理：
     * -    - 叶子节点：直接返回 null
     * -    - 只有一个子树：返回非空子树
     * -    - 左右子树都存在：寻找右子树最小节点作为替代
     * - 【最初实现中的问题点】
     * - 1. findMin 方法中混合了“查找最小节点”和“删除节点”的职责，
     * -    在查找过程中直接断开 pre.left，可能导致最小节点的右子树丢失。
     * - 2. 在左右子树都存在时，尝试将右子树最小节点整体替换当前节点，
     * -    并手动拼接左右子树：
     * -      - 这种“整体移动节点”的方式容易破坏 BST 结构
     * -      - 右子树中原本就包含最小节点，重新挂接会导致结构重复或顺序错误
     * - 3. 绕开了递归删除这一 BST 删除问题中最安全、最自然的机制，
     * -    反而引入了复杂且容易出错的指针操作。
     * - 【正确的抽象模型】
     * - - BST 删除的本质不是“移动节点”，而是：
     * -   1. 用后继节点（右子树最小值）替换当前节点的值
     * -   2. 再通过递归，在右子树中删除这个被使用过的值
     * - 【关键认知转变】
     * - - 查找最小节点：只负责“找”
     * - - 删除节点：必须交给 delete 的递归过程完成
     * - - 递归返回的新子树天然保证 BST 结构正确
     */
    public TreeNode deleteNode(TreeNode node, int key) {
        if (node == null) {
            return null;
        }

        if (node.val == key) {
            // 左子树为空
            if (node.left == null) {
                return node.right;
            }
            // 右子树为空
            if (node.right == null) {
                return node.left;
            }
            // 左右子树都存在
            TreeNode min = findMin(node.right);
            // 替换值
            node.val = min.val;
            // 删除右子树最小节点
            node.right = deleteNode(node.right, min.val);
        } else if (key < node.val) {
            node.left = deleteNode(node.left, key);
        } else {
            node.right = deleteNode(node.right, key);
        }
        return node;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
