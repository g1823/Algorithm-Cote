package leetCode.moderately;

/**
 * 208. 实现 Trie (前缀树)（数据结构）
 * 实现思路：
 * 1. 使用多叉树结构表示，每个节点包含一个长度为26的子节点数组（对应26个小写字母）
 * 2. 从根节点开始，每个字符对应一个子节点，沿着路径向下构建
 * 3. 使用 isEnd 标记记录哪些节点是字符串的结束节点
 * 相比使用Set的实现，Trie在前缀搜索时效率更高，时间复杂度为O(L)，其中L是查询字符串的长度
 */
class Trie {
    /**
     * 只有26个小写字母
     */
    Trie[] chars = new Trie[26];

    boolean isEnd = false;

    public Trie() {
    }

    public void insert(String word) {
        char[] chars = word.toCharArray();
        Trie node = this;
        for (char aChar : chars) {
            if (node.chars[aChar - 'a'] == null) {
                node.chars[aChar - 'a'] = new Trie();
            }
            node = node.chars[aChar - 'a'];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        char[] chars = word.toCharArray();
        Trie node = this;
        for (char aChar : chars) {
            if (node.chars[aChar - 'a'] == null) {
                return false;
            } else {
                node = node.chars[aChar - 'a'];
            }
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        char[] chars = prefix.toCharArray();
        Trie node = this;
        for (char aChar : chars) {
            if (node.chars[aChar - 'a'] == null) {
                return false;
            } else {
                node = node.chars[aChar - 'a'];
            }
        }
        return true;
    }
}