package leetCode.moderately;

/**
 * @author: gj
 * @description: 427. 建立四叉树
 */
public class Construct {

    public Node construct(int[][] grid) {
        return divide(grid, 0, 0, grid.length);
    }

    /**
     * 构建四叉树的核心方法。
     * <p>
     * 整体思路：
     * 1. 使用递归将当前 grid 区域划分为四个象限（左上、右上、左下、右下）。
     * 2. 若当前区域的边长 length == 1，则该区域只有一个值，可直接构建叶子节点返回。
     * 3. 对四个子区域分别构建四叉树，获得四个子节点。
     * 4. 若四个子节点均为叶子节点，并且它们的 val 值相同，则说明当前区域也是统一值区域，
     * 可将四个子节点合并为一个叶子节点，提高树的压缩效率。
     * 5. 否则，当前区域无法合并，需要构建一个非叶子节点，并将四个子节点挂载其下。
     * <p>
     * 本方法对应 LeetCode 427“建立四叉树”，依赖四叉树的压缩特性：
     * 若一块区域内部所有值都相同，则不需要继续拆分，直接作为叶子节点保存。
     */
    public Node divide(int[][] grid, int x, int y, int length) {
        if (length == 1) {
            return new Node(grid[x][y] == 1, true);
        }
        length = length / 2;
        // 左上
        Node lTop = divide(grid, x, y, length);
        // 右上
        Node rTop = divide(grid, x, y + length, length);
        // 左下
        Node lBot = divide(grid, x + length, y, length);
        // 右下
        Node rBot = divide(grid, x + length, y + length, length);

        // ⭐ 合并逻辑：四个子节点都是叶子并且值相同
        if (lTop.isLeaf && rTop.isLeaf && lBot.isLeaf && rBot.isLeaf
                && lTop.val == rTop.val
                && lTop.val == lBot.val
                && lTop.val == rBot.val) {
            return new Node(lTop.val, true);
        }
        // 否则返回非叶子节点
        return new Node(false, false, lTop, rTop, lBot, rBot);
    }





    public Node construct2(int[][] grid) {
        return build(grid, 0, 0, grid.length);
    }

    /**
     * 高性能四叉树构建：
     * 通过提前判断当前区域是否全部相同，
     * 若全部相同则直接构建叶子节点，
     * 否则划分四象限继续递归。
     */
    private Node build(int[][] grid, int x, int y, int len) {
        // 1. 判断当前区域是否全 0 或全 1
        if (isUniform(grid, x, y, len)) {
            return new Node(grid[x][y] == 1, true);
        }

        // 2. 无法直接合并，划分四象限
        int h = len / 2;

        Node topLeft = build(grid, x, y, h);
        Node topRight = build(grid, x, y + h, h);
        Node bottomLeft = build(grid, x + h, y, h);
        Node bottomRight = build(grid, x + h, y + h, h);

        // 3. 返回非叶子节点
        return new Node(
                false,
                false,
                topLeft,
                topRight,
                bottomLeft,
                bottomRight
        );
    }

    /**
     * 判断当前区域是否全部相同，从而决定是否能压缩成叶子节点。
     */
    private boolean isUniform(int[][] grid, int x, int y, int len) {
        int val = grid[x][y];
        for (int i = x; i < x + len; i++) {
            for (int j = y; j < y + len; j++) {
                if (grid[i][j] != val) {
                    return false;
                }
            }
        }
        return true;
    }

    static class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }
}
