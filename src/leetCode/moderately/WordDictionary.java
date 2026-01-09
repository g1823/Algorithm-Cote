package leetCode.moderately;

/**
 * @author: gj
 * @description: 211. 添加与搜索单词 - 数据结构设计
 */
public class WordDictionary {
    private WordDictionary[] children = new WordDictionary[26];
    private boolean isEnd = false;

    public void addWord(String word) {
        WordDictionary node = this;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new WordDictionary();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        return search(word, 0);
    }

    private boolean search(String word, int idx) {
        if (idx == word.length()) {
            return isEnd;
        }

        char c = word.charAt(idx);
        if (c == '.') {
            for (WordDictionary child : children) {
                if (child != null && child.search(word, idx + 1)) {
                    return true;
                }
            }
            return false;
        } else {
            WordDictionary child = children[c - 'a'];
            return child != null && child.search(word, idx + 1);
        }
    }
}