package leetCode.moderately;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gj
 * @description: 1268. 搜索推荐系统
 */
public class SuggestedProducts {
    public static void main(String[] args) {
        String[] products = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        String searchWord = "mouse";
        System.out.println(new SuggestedProducts().suggestedProducts(products, searchWord));
    }

    class TrieNode {
        TrieNode[] children;
        boolean isEnd;
        String word;

        public TrieNode() {
            children = new TrieNode[26];
        }

        public void insert(String word) {
            char[] chars = word.toCharArray();
            TrieNode node = this;
            for (char aChar : chars) {
                if (node.children[aChar - 'a'] == null) {
                    node.children[aChar - 'a'] = new TrieNode();
                }
                node = node.children[aChar - 'a'];
            }
            node.isEnd = true;
            node.word = word;
        }

        public List<String> search(String prefix) {
            char[] chars = prefix.toCharArray();
            TrieNode node = this;
            for (char aChar : chars) {
                if (node.children[aChar - 'a'] == null) {
                    return new ArrayList<>();
                }
                node = node.children[aChar - 'a'];
            }
            List<String> res = new ArrayList<>();
            dfs(node, res);
            return res;
        }

        private void dfs(TrieNode node, List<String> res) {
            if (res.size() == 3) {
                return;
            }
            if (node.isEnd) {
                res.add(node.word);
            }
            for (TrieNode child : node.children) {
                if (child != null) {
                    dfs(child, res);
                }
            }
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        char first = searchWord.charAt(0);
        TrieNode root = new TrieNode();
        for (String product : products) {
            if (product.charAt(0) == first) {
                root.insert(product);
            }
        }
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < searchWord.length(); i++) {
            List<String> list = root.search(searchWord.substring(0, i + 1));
            res.add(list);
        }
        return res;
    }
}
