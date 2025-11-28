package leetCode.simple;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 222. 完全二叉树的节点个数
 */
public class CountNodes {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        System.out.println(new CountNodes().countNodes(node1));
    }

    /**
     * 首先，可以直接采用深度优先或广度优先遍历全部节点统计个数。
     * 但是，针对完全二叉树，根据其性质只有最后一层的节点不满，其他行节点数都满，且最后一层节点都靠左。
     * 因此，可以通过二分查找的方式找到最后一层最靠左的空节点，进而计算完全二叉树的节点个数。
     * 步骤：
     * 1.全程找左树，得到二叉树的最大深度
     * 2.根据最大深度，计算最后一层节点数，即
     * 3.给最后一层节点做序号，0、1、2...2^maxDepth - 1
     * 4.通过二分查找，找到最后一层最靠左的空节点，若其为k,则节点个数 = 2^maxDepth - 1 + k
     *  *      - 若编号 mid 对应的节点存在，说明左半区都存在，向右区查找；
     *  *      - 若编号 mid 对应的节点不存在，说明右半区为空，向左区查找。
     *  *      - 二分结束时，left 为第一个不存在的节点编号，
     * 注意：
     * 关键点在于怎么二分查最后一层：
     * 参考一下规则：
     * | 编号 idx | 二进制表示 | 路径（0=左, 1=右） |
     * | ------ | ----- | ------------ |
     * | 0      | 000   | 左→左→左        |
     * | 1      | 001   | 左→左→右        |
     * | 2      | 010   | 左→右→左        |
     * | 3      | 011   | 左→右→右        |
     * | 4      | 100   | 右→左→左        |
     * | 5      | 101   | 右→左→右        |
     * | 6      | 110   | 右→右→左        |
     * | 7      | 111   | 右→右→右        |
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 1. 求最大深度（根节点深度为 0）
        int maxDepth = getDepth(root);

        // 2. 最后一层节点编号范围 [0, 2^h - 1]
        int left = 0, right = (1 << maxDepth) - 1;

        // 3. 二分查找最后一层第一个不存在的节点
        while (left <= right) {
            int mid = (left + right) / 2;
            if (exists(root, mid, maxDepth)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // 4. 计算总节点数
        return (1 << maxDepth) - 1 + left;
    }

    /**
     * 求树的最大深度（根节点深度为 0）
     */
    private int getDepth(TreeNode node) {
        int depth = 0;
        while (node.left != null) {
            depth++;
            node = node.left;
        }
        return depth;
    }

    /**
     * 判断最后一层编号为 idx 的节点是否存在。
     *
     * 思路：
     * 将 idx 转换为二进制路径，0 表示走左子树，1 表示走右子树。
     * 深度为 h，则路径长度为 h，从根节点出发依次沿路径判断是否为空。
     */
    private boolean exists(TreeNode root, int idx, int maxDepth) {
        int left = 0, right = (1 << maxDepth) - 1;
        for (int i = 0; i < maxDepth; i++) {
            int mid = (left + right) / 2;
            if (idx <= mid) {
                root = root.left;
                right = mid;
            } else {
                root = root.right;
                left = mid + 1;
            }
            if (root == null) return false;
        }
        return true;
    }
}
