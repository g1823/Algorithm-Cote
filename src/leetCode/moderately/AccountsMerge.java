package leetCode.moderately;

import java.util.*;

/**
 * @description: 721. 账户合并
 */
public class AccountsMerge {

    public static void main(String[] args) {
        List<List<String>> accounts = new ArrayList<>();
        List<String> account1 = new ArrayList<>();
        account1.add("John");
        account1.add("johnsmith@mail.com");
        account1.add("john_newyork@mail.com");
        List<String> account2 = new ArrayList<>();
        account2.add("John");
        account2.add("johnsmith@mail.com");
        account2.add("john00@mail.com");
        List<String> account3 = new ArrayList<>();
        account3.add("Mary");
        account3.add("mary@mail.com");
        List<String> account4 = new ArrayList<>();
        account4.add("John");
        account4.add("johnnybravo@mail.com");
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        System.out.println(new AccountsMerge().accountsMerge2(accounts));
    }

    /**
     * map（错误）
     * 用map存储email->index（不使用name的原因时name会重名）的映射，当一个email已经出现过，将本批所有的email都union到这个index上
     * 错误：
     * 若 用户A出现三次，第一次邮箱为 1，2 第二次邮箱为 3 4， 第三次邮箱为 1，3.按照当前逻辑，只会把1,2,3合并，然后4会才出现一次，不被合并
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailToIndex = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            List<String> account = accounts.get(i);
            String name = account.get(0);
            int index = -1;
            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);
                if (emailToIndex.containsKey(email)) {
                    index = emailToIndex.get(email);
                }
            }
            if (index == -1) {
                index = i;
            }
            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);
                emailToIndex.put(email, index);
            }
        }
        Map<Integer, Set<String>> indexToEmails = new HashMap<>();
        for (String email : emailToIndex.keySet()) {
            int index = emailToIndex.get(email);
            indexToEmails.computeIfAbsent(index, k -> new HashSet<>()).add(email);
        }
        List<List<String>> res = new ArrayList<>();
        for (Integer index : indexToEmails.keySet()) {
            Set<String> emails = indexToEmails.get(index);
            List<String> account = new ArrayList<>(emails);
            Collections.sort(account);
            account.add(0, accounts.get(index).get(0));
            res.add(account);
        }
        return res;
    }

    /**
     * 并查集
     * 1、收集所有邮箱，默认parent指向原下标index
     * 2、遍历邮箱，如果邮箱已存在，则合并两个parent(index)，用map存储是否出现过以及第一次出现时对应的下标
     */
    public List<List<String>> accountsMerge2(List<List<String>> accounts) {
        int n = accounts.size();
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        // 邮箱 -> 第一次出现的账户索引
        Map<String, Integer> emailToIndex = new HashMap<>();

        // 边遍历边合并，省去 emails 和 emailParents
        for (int i = 0; i < n; i++) {
            List<String> acc = accounts.get(i);
            for (int j = 1; j < acc.size(); j++) {
                String email = acc.get(j);
                if (emailToIndex.containsKey(email)) {
                    union(parent, rank, i, emailToIndex.get(email));
                } else {
                    emailToIndex.put(email, i);
                }
            }
        }

        // 收集每个根对应的邮箱集合
        Map<Integer, Set<String>> rootToEmails = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            Set<String> set = rootToEmails.computeIfAbsent(root, k -> new HashSet<>());
            // 只添加当前账户的邮箱（重复的会被 Set 去重）
            for (int j = 1; j < accounts.get(i).size(); j++) {
                set.add(accounts.get(i).get(j));
            }
        }

        // 构造结果
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Integer, Set<String>> entry : rootToEmails.entrySet()) {
            int root = entry.getKey();
            List<String> list = new ArrayList<>(entry.getValue());
            Collections.sort(list);
            // 使用根账户的名字
            list.add(0, accounts.get(root).get(0));
            result.add(list);
        }
        return result;
    }

    private void union(int[] parents, int[] ranks, int i, int j) {
        int parentI = find(parents, i);
        int parentJ = find(parents, j);
        if (parentI == parentJ) {
            return;
        }
        if (ranks[parentI] < ranks[parentJ]) {
            parents[parentI] = parentJ;
        } else {
            parents[parentJ] = parentI;
            if (ranks[parentI] == ranks[parentJ]) {
                ranks[parentI]++;
            }
        }
    }

    private int find(int[] parents, int i) {
        if (parents[i] != i) {
            parents[i] = find(parents, parents[i]);
        }
        return parents[i];
    }
}
