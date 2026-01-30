package leetCode.moderately;

import leetCode.help.TreeNode;

/**
 * @author: gj
 * @description: 1372. 二叉树中的最长交错路径
 */
public class LongestZigZag {
    int maxLen = 0;

    public int longestZigZag(TreeNode root) {
        dfs(root, 0, true);
        return maxLen;
    }

    private void dfs(TreeNode node, int curLen, boolean isLeft) {
        if (node == null) {
            maxLen = Math.max(maxLen, curLen);
            return;
        }
        // 父级节点是左节点
        if (isLeft) {
            // 当前节点必须为右节点
            if (node.right != null) {
                dfs(node.right, curLen + 1, false);
                maxLen = Math.max(maxLen, curLen);
            }
            // 从当前节点重新开始
            dfs(node.left, 1, false);
            dfs(node.left, 1, true);
        } else {
            // 当前节点必须为左节点
            if (node.left != null) {
                maxLen = Math.max(maxLen, curLen);
                dfs(node.left, curLen + 1, true);
            }
            // 从当前节点重新开始
            dfs(node.right, 1, true);
            dfs(node.right, 1, false);
        }
    }

    /**
     * 蛮力法：
     * 两遍dfs：
     * - 第一遍遍历所有节点，尝试以任意一个节点为起点
     * - 第二遍求当前节点的最长交错路径长度
     */
    class Solution {
        int maxLen = 0;

        public int longestZigZag(TreeNode root) {
            // 第一层 DFS：枚举每一个节点作为起点
            traverse(root);
            return maxLen;
        }

        /**
         * 枚举每个节点作为交错路径的起点
         */
        private void traverse(TreeNode node) {
            if (node == null) {
                return;
            }
            // 以当前节点作为起点，尝试两种方向
            if (node.left != null) {
                dfs(node.left, 1, 0);
            }
            if (node.right != null) {
                dfs(node.right, 1, 1);
            }
            traverse(node.left);
            traverse(node.right);
        }

        /**
         * @param node   当前节点
         * @param curLen 当前交错路径长度（边数）
         * @param dir    上一步方向：0=左，1=右
         */
        private void dfs(TreeNode node, int curLen, int dir) {
            if (node == null) {
                return;
            }
            maxLen = Math.max(maxLen, curLen);
            if (dir == 0) {
                // 上一步是左，这一步只能右
                if (node.right != null) {
                    dfs(node.right, curLen + 1, 1);
                }
            } else {
                // 上一步是右，这一步只能左
                if (node.left != null) {
                    dfs(node.left, curLen + 1, 0);
                }
            }
        }
    }

    /**
     * 树形动态规划：
     * 分析Solution可知，存在大量的重复计算，且当前节点只依赖于相邻节点的数据，因此完全可以使用动态规划来解决
     * - 思路总结：
     * - 1. 最初思路（蛮力，自顶向下）：
     * -    - 对每个节点作为起点，DFS 向下走。
     * -    - 每次 DFS 需要传递三个信息：
     * -      1) 当前节点
     * -      2) 当前路径长度
     * -      3) 当前节点是从父节点的左边还是右边走到的
     * -    - 每次延续路径时，严格遵循交错方向。
     * -    - 每次更新全局最大长度。
     * -
     * - 2. 蛮力存在问题：
     * -    - 重复遍历：每个节点被当作起点都会重复访问子树。
     * -    - 复杂度最坏 O(n^2)。
     * -
     * - 3. 尝试自底向上解决：
     * -    - 思路：从子节点递归返回每个方向的最长交错长度，再结合当前节点计算。
     * -    - 每个节点返回两个值：
     * -      - res[0]：当前节点出发，下一步往左的最长交错长度
     * -      - res[1]：当前节点出发，下一步往右的最长交错长度
     * -    - 递推公式：
     * -      - leftZig = 1 + left子树返回的 right方向长度
     * -      - rightZig = 1 + right子树返回的 left方向长度
     * -    - 全局最大值在每个节点计算时更新。
     * -
     * - 4. 为什么自底向上比自顶向下自然：
     * -    - 自顶向下：需要传递父节点方向，还要考虑从当前节点重新开始作为起点的路径，状态分裂复杂。
     * -    - 自底向上：每个节点天然就是“起点”，两个方向长度同时计算，天然覆盖整棵树，方向约束直接体现在递推公式里。
     * -
     * - 5. 我之前的两次问题点：
     * -    ① 初版蛮力：只依赖父节点方向延续路径，漏掉了“从当前节点重新开始作为起点”的情况。
     * -    ② 初版自底向上尝试：返回两个方向的长度时，错误地用 max(left[0], left[1]) 和 max(right[0], right[1])，破坏了交错方向约束。
     * -       正确做法是只能取子节点相反方向长度。
     * -
     * - 总结：
     * -  - 每个节点返回两个方向的最长交错路径长度
     * -  - 递归自底向上更新全局最大值
     * -  - 时间复杂度 O(n)，空间复杂度 O(h)（递归栈）
     */
    class Solution2 {

        int maxLen = 0;

        public int longestZigZag(TreeNode root) {
            dfs(root);
            return maxLen - 1;
        }

        /**
         * @param node 当前节点
         * @return int[2]
         * res[0] = 从当前节点出发，第一步往左的最长交错路径
         * res[1] = 从当前节点出发，第一步往右的最长交错路径
         */
        private int[] dfs(TreeNode node) {
            if (node == null) {
                return new int[]{0, 0};
            }

            int[] left = dfs(node.left);
            int[] right = dfs(node.right);

            // 第一步往左：到左子节点后下一步必须往右
            int leftZig = 1 + left[1];
            // 第一步往右：到右子节点后下一步必须往左
            int rightZig = 1 + right[0];

            // 更新全局最大值
            maxLen = Math.max(maxLen, Math.max(leftZig, rightZig));

            return new int[]{leftZig, rightZig};
        }
    }
}
