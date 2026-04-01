package leetCode.difficult;

import java.util.*;

/**
 * @author: gj
 * @description: 315. 计算右侧小于当前元素的个数
 */
public class CountSmaller {

    /**
     * * 一、问题分析（从直觉到优化）
     * * 1. 蛮力解法
     * *    对于每个元素 nums[i]，遍历其右侧所有元素 nums[j] (j > i)，统计比它小的数量
     * *    时间复杂度 O(n^2)
     * *    问题：存在大量重复比较，例如同一对元素可能被多次比较
     * *
     * * 2. 尝试单调栈（失败思路）
     * *    从右往左遍历，尝试用单调栈维护右侧信息
     * *    问题：
     * *    - 单调栈会“丢弃元素”（为了维护单调性会弹栈）
     * *    - 本题要求统计“所有右侧更小元素”，不能丢任何一个
     * *    结论：单调栈适合“找最近更大/更小元素”，不适合做“计数问题”
     * *
     * * 3. 优化方向思考
     * *    目标转化为：
     * *    - 维护一个“右侧元素集合”
     * *    - 支持：
     * *        ① 插入元素
     * *        ② 查询：有多少元素比当前值小
     * *    可选方案：
     * *    - 有序数组 + 二分（插入 O(n)，整体仍 O(n^2)）
     * *    - 平衡 BST / 树状数组（可做到 O(n log n)）
     * *
     * * 4. 归并排序思路
     * *    关键转化：
     * *    将问题转化为： 在排序过程中，统计“右边元素比左边元素小”的次数
     * *    使用归并排序：
     * *    - 左半部分：原数组中靠左的元素
     * *    - 右半部分：原数组中靠右的元素
     * *    在 merge 过程中：
     * *    如果：右侧元素 < 左侧元素
     * *    则：说明这个右侧元素是“左侧元素的一个更小元素”
     * *
     * * 二、核心思想（归并统计）
     * *    在 merge 时维护变量：rightCount：右侧已经放入 merge 数组的元素个数
     * *    规则：
     * *    1. 若 nums[left] <= nums[right]
     * *       → 左元素进入结果数组
     * *       → result[leftIndex] += rightCount
     * *    2. 若 nums[right] < nums[left]
     * *       → 右元素先进入
     * *       → rightCount++
     * *    含义： rightCount 表示：已经确定在当前左元素之前的“右侧更小元素个数”
     * *
     * * 三、关键技巧
     * * 1. 排序的是 index（下标数组），不是 nums
     * *    - 因为排序过程中元素位置会变
     * *    - 需要通过 index 找回原始位置，更新 result
     * * 2. 使用 nums[index[i]] 进行比较
     * * 3. 使用 result[index[i]] 更新答案
     * * 4. 必须使用 <= 保证稳定性:避免相等元素被错误统计
     * *
     * * 四、解题步骤
     * * 1. 初始化 index 数组：index[i] = i
     * * 2. 对 index 进行归并排序（比较 nums[index[i]]）
     * * 3. 在 merge 过程中：
     * *        - 维护 rightCount
     * *        - 更新 result[index[i]]
     * * 4. 最终 result 即答案
     * *
     * * 五、复杂度分析
     * * 时间复杂度：O(n log n)
     * * 空间复杂度：O(n)
     * *
     * * 六、总结
     * * 本题本质是：
     * *    👉 “在排序过程中统计逆序关系（右侧更小）”
     * * 与以下问题同类：
     * *    - 逆序对问题
     * *    - 493. 翻转对
     * *    - 327. 区间和的个数
     * * 核心思想：
     * *    👉 排序 + 统计
     */
    public List<Integer> countSmaller(int[] nums) {
        // 排序时直接排序原数组的下标
        int[] temp = new int[nums.length];
        // 记录每个下标右侧小于其值的元素个数
        int[] resultNum = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            temp[i] = i;
        }
        mergeSort(nums, temp, 0, nums.length - 1, resultNum);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < resultNum.length; i++) {
            result.add(resultNum[i]);
        }
        return result;
    }

    private int[] mergeSort(int[] nums, int[] temp, int left, int right, int[] result) {
        if (left >= right) {
            // 只有一个元素时，肯定有序，直接返回
            return new int[]{temp[left]};
        }
        int mid = left + (right - left) / 2;
        // 将左侧区间和右侧区间均排序
        int[] leftSortNums = mergeSort(nums, temp, left, mid, result);
        int[] rightSortNums = mergeSort(nums, temp, mid + 1, right, result);
        // 合并两侧区间
        int[] mergeNums = new int[right - left + 1];
        int i = 0, l = 0, r = 0, count = 0;
        while (l < leftSortNums.length && r < rightSortNums.length) {
            // 左侧小于右侧，直接将左侧元素加入结果中
            if (nums[leftSortNums[l]] <= nums[rightSortNums[r]]) {
                int idx = leftSortNums[l];
                mergeNums[i++] = idx;
                l++;
                // 左侧元素一旦进入，说明右侧不会再有比其小的元素，则累计当前已经累计的右侧比其小的元素个数
                // 注意：leftSortNums[l] 表示原数组的下标。
                result[idx] += count;
            }
            // 右侧小于左侧，将右侧元素加入结果中，同时说明对于左侧元素而言，新增了一个比其小的右侧元素
            else {
                mergeNums[i++] = rightSortNums[r++];
                // 右侧小于当前元素的个数加一
                count++;
            }
        }
        while (l < leftSortNums.length) {
            int idx = leftSortNums[l];
            mergeNums[i++] = idx;
            l++;
            result[idx] += count;
        }
        while (r < rightSortNums.length) {
            mergeNums[i++] = rightSortNums[r++];
        }
        return mergeNums;
    }

    /**
     * 尝试使用线段树实现{@link leetCode.difficult.SeparateSquares.Solution3}
     */
    class solution2 {
        /**
         * 先不用数组“隐式表示一棵完全二叉树”，直接用真实二叉树实现
         */
        class SegmentTree {
            class Node {
                int l, r;
                int count;
                Node left, right;

                // l=r时表示叶子节点
                public Node(int l, int r, int count) {
                    this.l = l;
                    this.r = r;
                    this.count = count;
                }
            }

            Node root;

            /**
             * 构造线段树，因为本题目会离散原数组，并映射到1-n之间，因此线段树节点的左右边界为1-n之间
             *
             * @param n 数组大小
             */
            public SegmentTree(int n) {
                root = build(1, n);
            }

            private Node build(int l, int r) {
                if (l == r) {
                    // 构造节点时，每个数出现0次
                    return new Node(l, l, 0);
                }
                Node node = new Node(l, r, 0);
                int mid = l + (r - l) / 2;
                node.left = build(l, mid);
                node.right = build(mid + 1, r);
                return node;
            }

            /**
             * 本体特殊，不会更新区间，仅更新单节点，且每次更新都是出现次数+1，因此只需要传入更新的节点值
             *
             * @param index 节点值
             */
            public void update(int index) {
                update(root, index);
            }

            /**
             * 因为不存在更新区间情况，所以也不需要延迟更新了，默认更新为+1
             *
             * @param node  当前节点
             * @param index 待更新节点
             */
            private void update(Node node, int index) {
                // 不在当前区间内
                if (node.l > index || node.r < index) {
                    return;
                }
                // 待更新节点就是当前节点
                if (node.l == node.r) {
                    node.count++;
                    return;
                }
                int mid = node.l + (node.r - node.l) / 2;
                // 待更新节点在左区间内
                if (index <= mid) {
                    update(node.left, index);
                }
                // 待更新节点在右区间内
                else {
                    update(node.right, index);
                }
                // 更新结束，维护当前节点代表区间的出现次数
                node.count = node.left.count + node.right.count;
            }

            /**
             * 查询区间[l, r]内元素出现的次数
             *
             * @param l 区间左边界
             * @param r 区间右边界
             * @return 区间内元素出现的次数
             */
            public int query(int l, int r) {
                return query(root, l, r);
            }

            private int query(Node node, int l, int r) {
                // 不在当前区间内
                if (node.l > r || node.r < l) {
                    return 0;
                }
                // 当前区间完全被覆盖在查询区间内
                if (node.l >= l && node.r <= r) {
                    return node.count;
                }
                // 部分覆盖的情况
                return query(node.left, l, r) + query(node.right, l, r);
            }
        }

        public List<Integer> countSmaller(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num);
            }
            int[] sortedNums = set.stream().sorted().mapToInt(Integer::intValue).toArray();
            // 存储真实值和离散映射值的对应关系
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < sortedNums.length; i++) {
                map.put(sortedNums[i], i + 1);
            }
            SegmentTree segmentTree = new SegmentTree(sortedNums.length);
            List<Integer> result = new ArrayList<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                // 获取当前值的离散映射值
                int index = map.get(nums[i]);
                // 查询小于当前值的元素个数
                result.add(segmentTree.query(1, index - 1));
                // 更新当前值的出现次数
                segmentTree.update(index);
            }
            Collections.reverse(result);
            return result;
        }
    }

    /**
     * 尝试使用线段树实现{@link leetCode.difficult.SeparateSquares.Solution3}
     * 采用数组隐式表达二叉树
     */
    class solution3 {

        class SegmentTree {
            int[] tree;
            int n;

            public SegmentTree(int n) {
                this.n = n;
                /*
                线段树用数组存储，数组大小取 4 * n，原因如下：
                1. 线段树叶子节点对应原数组的 n 个元素。
                2. 树的高度 h = log2(n) 向上取整 + 1。
                3. 完全二叉树最多有 2^h - 1 个节点，即 2^(向上取整(log2(n)) + 1) - 1 = 2 * 2^(向上取整(log2(n))) - 1。
                4. 因为 2^(向上取整(log2(n))) 是“不小于 n 的最小 2 的幂”，它一定小于 2n（当 n>0 时）。
                5. 所以节点数 < 2 * (2n) - 1 = 4n - 1。
                因此，分配 4n 大小的数组足以安全存储所有节点（若索引从 1 开始，可再加 5 避免越界）。
                 */
                tree = new int[4 * n];
            }

            public void update(int index) {
                update(1, 1, n, index);
            }

            /**
             * 采用数组表示线段树，需要额外传递当前节点的区间信息
             *
             * @param node  当前节点
             * @param start 当前节点的区间左边界
             * @param end   当前节点的区间右边界
             * @param key   待更新节点
             */
            private void update(int node, int start, int end, int key) {
                // 到达叶子节点
                if (start == end) {
                    tree[node]++;
                    return;
                }
                int mid = (start + end) / 2;
                // 不在当前区间内
                if (key <= mid) {
                    // 左节点 = 2 * node（节点从1开始，不用+1了）
                    update(node * 2, start, mid, key);
                } else {
                    // 右节点 = 2 * node + 1
                    update(node * 2 + 1, mid + 1, end, key);
                }
                tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }

            /**
             * 查询区间[l, r]内元素出现的次数
             *
             * @param l 区间左边界
             * @param r 区间右边界
             * @return 区间内元素出现的次数
             */
            public int query(int l, int r) {
                return query(1, 1, n, l, r);
            }

            private int query(int node, int start, int end, int l, int r) {
                // 不在当前区间内
                if (start > r || end < l) {
                    return 0;
                }
                // 当前区间完全被覆盖在查询区间内
                if (l <= start && end <= r) {
                    return tree[node];
                }
                // 部分覆盖的情况
                int mid = (start + end) / 2;
                return query(node * 2, start, mid, l, r) + query(node * 2 + 1, mid + 1, end, l, r);
            }
        }

        public List<Integer> countSmaller(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num);
            }
            int[] sortedNums = set.stream().sorted().mapToInt(Integer::intValue).toArray();
            // 存储真实值和离散映射值的对应关系
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < sortedNums.length; i++) {
                map.put(sortedNums[i], i + 1);
            }
            SegmentTree segmentTree = new SegmentTree(sortedNums.length);
            List<Integer> result = new ArrayList<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                // 获取当前值的离散映射值
                int index = map.get(nums[i]);
                // 查询小于当前值的元素个数
                result.add(segmentTree.query(1, index - 1));
                // 更新当前值的出现次数
                segmentTree.update(index);
            }
            Collections.reverse(result);
            return result;
        }
    }


    /**
     * * 树状数组
     * * 一、问题本质
     * * 对于每个 nums[i]，统计其右侧有多少元素 < nums[i]
     * * 转化为：
     * * 从右往左遍历数组，对于当前元素 nums[i]：
     * *      统计“已经遍历过的元素中，有多少 < nums[i]”
     * * ⇒ 本质问题：
     * *      动态维护一个“频率数组”，支持：
     * *          1）单点更新：某个值出现一次
     * *          2）前缀查询：统计 ≤ 某值的数量
     * *
     * * 二、为什么用树状数组？
     * * 我们需要高效支持：
     * *      add(x, 1)      → 某个值出现
     * *      query(x)       → 查询 ≤ x 的数量
     * *
     * * 树状数组可以在 O(log n) 时间完成：
     * *      单点更新 + 前缀和查询
     * *
     * * 本质：
     * *      用“二进制结构”来高效拆分区间
     * *
     * * 三、离散化（必须）
     * * 原数组值域可能很大（[-1e9,1e9]），无法作为数组下标
     * *
     * * 需要将 nums 映射为排名 rank ∈ [1, n]
     * * 例如：
     * *      nums = [5,2,6,1]
     * *      sorted = [1,2,5,6]
     * *      映射：1→1, 2→2, 5→3, 6→4
     * *
     * * 四、树状数组核心定义
     * * lowbit(x) = x & -x
     * *
     * * 含义：
     * *      返回 x 的二进制中“最低位的 1”所对应的值（即 2^k）
     * *
     * * 例：
     * *      x = 6 (110)  → lowbit = 2 (010)
     * *      x = 12(1100) → lowbit = 4 (0100)
     * *
     * * tree[x] 的真正含义（极其关键）
     * * tree[x] 并不是存单点值，而是存一个区间和：
     * *      tree[x] = sum(nums[x - lowbit(x) + 1 ... x])
     * * 即：
     * *      每个节点管理一个“以 x 结尾”的区间
     * *
     * * 五、query(x) 为什么正确（严格证明）
     * * query(x) 过程：
     * *      while (x > 0) {
     * *          sum += tree[x];
     * *          x -= lowbit(x);
     * *      }
     * * 区间拆分形式
     * * 定义：
     * *      x0 = x
     * *      x1 = x0 - lowbit(x0)
     * *      x2 = x1 - lowbit(x1)
     * *      ...
     * *      xm = 0
     * * 每一步对应区间：
     * *      Ii = [xi - lowbit(xi) + 1 , xi]
     * *
     * * 不重叠证明
     * * 因为：
     * *      xi+1 = xi - lowbit(xi)
     * * ⇒
     * *      Ii.left = xi - lowbit(xi) + 1
     * *      Ii+1.right = xi+1 = xi - lowbit(xi)
     * * ⇒
     * *      Ii+1.right = Ii.left - 1
     * * ✔ 区间严格相邻，不可能重叠
     * *
     * * 覆盖性证明（不遗漏）
     * * 每个区间长度：
     * *      |Ii| = lowbit(xi)
     * *
     * * 总长度：
     * *      Σ lowbit(xi) = x
     * * ⇒ 区间总长度等于 x，且不重叠
     * * ⇒ 完整覆盖 [1, x]
     * *
     * * 本质（最重要理解）
     * * query(x) 实际在做：
     * *      x = Σ (2^k)   （二进制展开）
     * * 每个 lowbit(xi) 对应一个 2^k
     * * ⇒ 把 [1, x] 按“二进制块”拆分
     * * 举例：
     * *      x = 13 = 1101
     * * 拆分：
     * *      [13,13]  (1)
     * *      [9,12]   (4)
     * *      [1,8]    (8)
     * * ⇒ 完整覆盖 [1,13]
     * *
     * * 六、add(x, delta) 为什么正确
     * * add(x, delta) 过程：
     * *      while (x <= n) {
     * *          tree[x] += delta;
     * *          x += lowbit(x);
     * *      }
     * * 目标
     * * 将“位置 x 的变化”更新到所有“包含 x 的区间”
     * * tree[i] 覆盖区间：
     * *      [i - lowbit(i) + 1 , i]
     * * 若区间包含 x，需要满足：
     * *      i - lowbit(i) + 1 ≤ x ≤ i
     * * 为什么 x += lowbit(x) 能找到这些区间？
     * * 每次 x += lowbit(x)，会跳到：
     * *      覆盖范围更大的区间（父节点）
     * * 本质：
     * *      相当于在一棵隐式树中，从叶子不断向上更新祖先节点
     * *
     * * 七、树状数组与“树结构”的关系
     * * tree[x] 对应区间长度：
     * *      lowbit(x) = 2^k
     * * 所有区间长度都是 2 的幂
     * * 区间之间满足：
     * *      要么不相交
     * *      要么完全包含
     * * ⇒ 形成一个“层级结构”（类似完全二叉树）
     * * 本质：
     * *      树状数组 = 用二进制隐式表示的线段树
     * *
     * * 八、为什么数组大小是 n + 1？
     * * 树状数组必须使用 1-based 索引
     * * 原因：
     * *      lowbit(0) = 0
     * * 如果使用 0：
     * *      x += lowbit(x) → 死循环
     * * 且：
     * *      区间定义 [x - lowbit(x) + 1 , x] 在 x=0 时无意义
     * * ⇒ 必须从 1 开始
     * * ⇒ 数组大小必须为 n + 1（下标 1 ~ n）
     * *
     * * 九、完整算法流程
     * * 1. 离散化：将 nums 映射为 rank
     * * 2. 初始化树状数组
     * * 3. 从右往左遍历：
     * *      - query(rank - 1)：统计更小元素数量
     * *      - add(rank, 1)：加入当前元素
     * *
     * * 十、复杂度
     * * 时间复杂度：
     * *      O(n log n)
     * * 空间复杂度：
     * *      O(n)
     * *
     * * 十一、终极总结（核心三点）
     * * 1、tree[x] 存区间和：
     * *      [x - lowbit(x) + 1 , x]
     * *
     * * 2、query：
     * *      利用 lowbit 将 [1,x] 拆成若干“二进制区间块”
     * *
     * * 3、 add：
     * *      更新所有“覆盖该点”的区间（向父节点传播）
     * *
     * * ⇒ 树状数组本质：
     * *      用二进制结构实现的“前缀和分块 + 隐式线段树”
     * *
     */
    class solution4 {
        public List<Integer> countSmaller(int[] nums) {
            // 离散化原数组
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num);
            }
            int[] sortedNums = set.stream().sorted().mapToInt(Integer::intValue).toArray();
            // 存储真实值和离散映射值的对应关系
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < sortedNums.length; i++) {
                map.put(sortedNums[i], i + 1);
            }
            // 倒叙遍历，使用树状数组维护每个离散区间出现的次数
            /**
             * tree[x] 覆盖区间：[x - lowbit(x) + 1 , x]
             * 如果x = 0,则表示区间[1, 0]，非法状态
             * query中，x=0认为所有的1已经清空，直接结束了
             * update中，初始x=0,x += index & -x，x一直等于0，会死循环
             */
            int[] tree = new int[sortedNums.length + 1];
            List<Integer> result = new ArrayList<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                int index = map.get(nums[i]);
                result.add(query(tree, index - 1));
                add(tree, index);
            }
            Collections.reverse(result);
            return result;
        }

        private void add(int[] tree, int index) {
            while (index < tree.length) {
                tree[index]++;
                index += index & -index;
            }
        }

        private int query(int[] tree, int index) {
            int sum = 0;
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }

}
