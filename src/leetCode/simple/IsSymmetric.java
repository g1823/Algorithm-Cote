package leetCode.simple;

import leetCode.help.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package leetCode
 * @Date 2024/9/8 16:32
 * @Author gaojie
 * @description: 101. 对称二叉树
 */
public class IsSymmetric {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(9);
        TreeNode node1 = new TreeNode(-42);
        TreeNode node2 = new TreeNode(-42);
        TreeNode node3 = new TreeNode(76);
        TreeNode node4 = new TreeNode(76);
        TreeNode node5 = new TreeNode(13);
        TreeNode node6 = new TreeNode(13);
        root.left = node1;
        root.right = node2;
        node1.right = node3;
        node2.left = node4;
        node3.right = node5;
        node4.right = node6;
        new IsSymmetric().isSymmetric(root);
    }


    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        List<TreeNode> nodeList = new ArrayList<>();
        nodeList.add(root.left);
        nodeList.add(root.right);
        return bfs(nodeList);
    }

    /**
     * 一次将下一层的元素全部取出，包括空，一层一层的判断是否对称
     *
     * @param nodeList 待比较元素
     * @return 是否对称
     */
    public boolean bfs(List<TreeNode> nodeList) {
        int length = nodeList.size();
        // 判断本层元素是否对称，优化，除了根节点外，其他层一定是偶数个
        // for (int l = 0, r = length - 1; l != r && l < length && r > 0; l++, r--) {
        for (int l = 0, r = length - 1; l < r; l++, r--) {
            TreeNode leftNode = nodeList.get(l);
            TreeNode rightNode = nodeList.get(r);
            if (leftNode == null && rightNode == null) continue;
            if (leftNode == null || rightNode == null) return false;
            if (leftNode.val != rightNode.val) return false;
        }

        List<TreeNode> newNodeList = new ArrayList<>();
        boolean allIsNull = true;
        for (int i = 0; i < length; i++) {
            TreeNode treeNode = nodeList.get(i);
            if (treeNode != null) {
                allIsNull = false;
                newNodeList.add(treeNode.left);
                newNodeList.add(treeNode.right);
            } else {
                newNodeList.add(null);
                newNodeList.add(null);
            }
        }
        if (allIsNull) return true;
        return bfs(newNodeList);
    }


    /**
     * 分治：
     * 如果一个树的左子树与右子树镜像对称，那么这个树是对称的。
     * 如果同时满足下面的条件，两个树互为镜像：
     * 它们的两个根结点具有相同的值
     * 每个树的右子树都与另一个树的左子树镜像对称
     * 通过「同步移动」两个指针的方法来遍历这棵树，p 指针和 q 指针一开始都指向这棵树的根，随后 p 右移时，q 左移，p 左移时，q 右移。
     * 每次检查当前 p 和 q 节点的值是否相等，如果相等再判断左右子树是否对称。
     */
    public boolean isSymmetric2(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }


}
