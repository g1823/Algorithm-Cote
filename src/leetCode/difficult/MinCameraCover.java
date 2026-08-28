package leetCode.difficult;

import leetCode.help.TreeNode;

/**
 * @description: 968. 监控二叉树
 */
public class MinCameraCover {
    /**
     * 动态规划(树形dp)
     * 按照题目要求，相当于不可连续三级没有摄像头，因此需要每个节点记录多种状态
     * 实际上，记录分两个维度，有无摄像头和是否被覆盖，那么可以这样记录：
     * - 0 = 该节点有摄像头时，子树最小摄像头数
     * - 1 = 该节点被覆盖（没摄像头）时，子树最小摄像头数
     * - 2 = 该节点未覆盖（没摄像头）时，子树最小摄像头数
     * 状态转移：
     * - cur[0]表示当前节点放置摄像头，那么就无需考虑子节点状态了，所有状态都是合法的，直接取 1 + min(两个子节点不同组合下的最小值)
     * - cur[1]表示当前节点被覆盖，那么就需要左右两个子节点至少有一个放置了摄像头，
     * - - 1、左右子节点都有摄像头 dpl[0] + dpr[0]
     * - - 2、左子节点有摄像头，此时右子节点必须被覆盖 dpl[0] + dpr[1]
     * - - 3、右子节点有摄像头，此时左子节点必须被覆盖 dpr[0] + dpl[1]
     * - cur[2]表示当前节点未被覆盖，则需要左右两个子节点均没有摄像头，还需要保证转移合法，那就直接取值dpl[1] + dpr[1]
     */
    public int minCameraCover(TreeNode root) {
        int[] res = dfs(root);
        // 根节点不能未覆盖，未覆盖不合法，没有其他父节点覆盖他了
        return Math.min(res[0], res[1]);
    }

    private int[] dfs(TreeNode node) {
        // 空节点
        if (node == null) {
            /*
             * 空节点返回 (INF, 0, 0)，三个值缺一不可，分别解释：
             *
             * [0] = INF（无穷大）
             *       空节点不存在"自己放摄像头"的情况。若返回 0，相当于白送一个免费摄像头，
             *       父节点会被错误判定为"被覆盖"，结果偏小。
             *       反例：单节点树。若空节点 [0]=0，根的 dp1 = min(0+0,...) = 0，
             *       答案 min(dp0=1, dp1=0) = 0，而正确答案是 1（必须自己装）。
             *       注意不能取 Integer.MAX_VALUE：dp1 计算里两个 INF 相加会溢出成负数，
             *       反而被 min 选中，导致结果错乱，故取 MAX/2 作为安全的"无穷大"。
             *
             * [1] = 0
             *       空节点不存在，无所谓"被覆盖"，摄像头数贡献 0。
             *       关键依赖：叶子节点的 dp2 = dp1[左空] + dp1[右空] = 0 + 0 = 0；
             *       只有一个子树时，dp1/dp2 转移中的空侧也依赖此值为 0。
             *
             * [2] = 0
             *       空节点"未被覆盖"，摄像头数贡献 0。
             */
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }
        int[] l = dfs(node.left);
        int[] r = dfs(node.right);
        // 当前节点放置摄像头，子节点任意状态都合法，取各自子树最小值之和
        int i0 = 1 + minOf(l) + minOf(r);
        int i1 = Math.min(
                l[0] + r[0],
                Math.min(l[0] + r[1], l[1] + r[0])
        );
        int i2 = l[1] + r[1];
        return new int[]{i0, i1, i2};
    }

    private int minOf(int[] state) {
        return Math.min(state[0], Math.min(state[1], state[2]));
    }

    /**
     * 动态规划(树形dp)
     * 解法1中，返回三个状态是冗余的，可以证明贪心的尽可能由父级装填是最优的。因此可以直接压缩状态
     */
    class solution2 {
        int res = 0;

        public int minCameraCover(TreeNode root) {
            int status = dfs(root);
            if (status == 2) {
                res++;
            }
            return res;
        }

        /**
         * 0 = 有摄像头   1 = 被覆盖   2 = 未覆盖
         */
        private int dfs(TreeNode node) {
            // 空节点认为被覆盖，不影响上层节点
            if (node == null) {
                return 1;
            }
            int l = dfs(node.left);
            int r = dfs(node.right);
            // 子节点有一个未覆盖，当前节点必须放一个
            if (l == 2 || r == 2) {
                res++;
                return 0;
            }
            // 子节点均被覆盖，当前节点不放摄像头，期望父级放摄像头覆盖
            if(l == 1 && r == 1){
                return 2;
            }
            // 子节点有一个有摄像头，当前节点被覆盖
            if(l == 0 || r == 0){
                return 1;
            }

            // 永远不会执行：三个分支已穷尽 (0/1/2) 的全部 9 种组合，此句仅为满足 Java 编译要求
            return 1;
        }
    }
}
