package leetCode.moderately;

import leetCode.help.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 173. 二叉搜索树迭代器
 * 简单说：
 * - 遍历顺序应该是先遍历左子树，再遍历根节点，最后遍历右子树
 * - 由于空间复杂度要求，不能一次遍历，必须边遍历边获取结果，
 * - 那么难点就是遍历某个节点的时候，需要记忆父级，从而可以遍历到父级以及父级的右子树。
 * - 其实可以使用栈来保存，初始时从根节点一直遍历左子树，直到叶子节点，那么栈顶就是最左侧节点，也就是最小节点。
 * - 然后依次出栈，在出栈时，对出栈节点做如下如理：对出栈节点的右节点不断遍历左子树，直到叶子节点。
 * - 根据二叉搜索树的性质，按上述流程入栈的顺序就是正确的遍历顺序。
 * 一、问题本质
 * ------------------------------------------------------------
 * 二叉搜索树（BST）的中序遍历结果是一个严格递增的序列。
 * 本题要求实现一个“按中序顺序逐个返回节点值”的迭代器，
 * 并且要求：
 * - next() 均摊时间复杂度 O(1)
 * - 额外空间复杂度 O(h)，h 为树高
 * 这意味着：
 * - 不能一次性把中序遍历结果全部存到数组中（那样是 O(n) 空间）
 * - 必须“边遍历，边返回”，并且保存的状态足够少
 * 二、核心思路（如何从递归中序遍历转为迭代）
 * ------------------------------------------------------------
 * 递归中序遍历的访问顺序是：
 * -     左子树 -> 当前节点 -> 右子树
 * 递归版本中，函数调用栈隐式地保存了：
 * - 当前节点
 * - 以及“接下来该去访问哪里”
 * 本题的关键在于：
 * - 用一个显式的栈，来模拟递归调用栈
 * - 栈中保存的不是“已经访问过的节点”，
 * -  而是“未来一定会访问到的节点路径”
 * 三、不变量（最重要）
 * ------------------------------------------------------------
 * 栈 stack 始终满足以下不变量：
 * 1）stack 中保存的是：尚未访问、但之后一定会访问到的节点
 * 2）stack 栈顶元素，一定是“下一个要返回的最小节点”
 * 只要这个不变量成立：
 * - next() 的正确性就能保证
 * - hasNext() 只需要判断 stack 是否为空
 * 四、构造函数为什么要压“整条左链”
 * ------------------------------------------------------------
 * 中序遍历的第一个访问节点，一定是整棵树中“最左”的节点。
 * 因此在构造函数中：
 * - 从 root 开始，一路向左走
 * - 将路径上的所有节点压入栈中
 * 这样做的结果是：
 * - 栈顶就是全树最小值
 * - 不变量在一开始就成立
 * 五、next() 的思考过程
 * ------------------------------------------------------------
 * next() 的目标只有一个：
 * - 返回当前最小的节点值
 * - 并为“下一次 next()”恢复不变量
 * 具体分两步：
 * 1）弹出栈顶节点 curNode
 * - 它一定是当前最小值（由不变量保证）
 * 2）处理 curNode 的右子树
 * 情况 A：curNode.right == null
 * --------------------------------
 * - 当前节点已经没有右子树
 * - 中序遍历中，下一个节点一定在栈中（某个祖先节点）
 * - 不需要做任何额外操作
 * 情况 B：curNode.right != null
 * --------------------------------
 * - 中序遍历中，下一个节点一定在右子树中
 * - 且一定是“右子树的最左节点”
 * - 因此从 curNode.right 开始，一路向左
 * 将整条左链压入栈中
 * 这样就能重新恢复“不变量”
 * 六、为什么 hasNext() 只需判断 stack 是否为空
 * ------------------------------------------------------------
 * 是否还有下一个节点 ⇔ 是否还有“未来一定会访问到的节点”
 * 而这些节点，全部保存在 stack 中。
 * 因此：
 * - stack 为空 → 中序遍历结束
 * - stack 非空 → 一定还能继续 next()
 * 不需要额外的 flag 或状态变量。
 * 七、时间复杂度分析（均摊 O(1) 的原因）
 * ------------------------------------------------------------
 * 虽然 next() 中存在 while 循环，
 * 但每个节点：
 * - 只会被压栈一次
 * - 只会被弹栈一次
 * 因此总操作次数是 O(n)，
 * 平均到每一次 next()，时间复杂度是均摊 O(1)。
 */
public class BSTIterator {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(7);
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(15);
        TreeNode node3 = new TreeNode(9);
        TreeNode node4 = new TreeNode(20);
        root.left = node1;
        root.right = node2;
        node2.left = node3;
        node2.right = node4;
//        BSTIterator bstIterator = new BSTIterator(root);
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.hasNext());
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.hasNext());
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.hasNext());
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.hasNext());

        TreeNode root1 = new TreeNode(1);
        TreeNode node11 = new TreeNode(2);
        root1.right = node11;
        BSTIterator bstIterator1 = new BSTIterator(root1);
        System.out.println(bstIterator1.hasNext());
        System.out.println(bstIterator1.next());
        System.out.println(bstIterator1.hasNext());
        System.out.println(bstIterator1.next());
        System.out.println(bstIterator1.hasNext());

    }

    Deque<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        TreeNode t = root;
        while (t != null) {
            stack.push(t);
            t = t.left;
        }
    }

    public int next() {
        TreeNode curNode = stack.pop();
        TreeNode t = curNode.right;
        if (t == null) {
            return curNode.val;
        } else {
            while (t != null) {
                stack.push(t);
                t = t.left;
            }
        }
        return curNode.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
