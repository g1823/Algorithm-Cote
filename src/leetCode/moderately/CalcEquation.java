package leetCode.moderately;

import java.util.*;

/**
 * @author: gj
 * @description: 399. 除法求值
 */
public class CalcEquation {
    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        equations.add(Arrays.asList("x1", "x2"));
        equations.add(Arrays.asList("x2", "x3"));
        equations.add(Arrays.asList("x3", "x4"));
        equations.add(Arrays.asList("x4", "x5"));
        double[] values = new double[]{3.0, 4.0, 5.0, 6.0};
        List<List<String>> queries = new ArrayList<>();
        queries.add(Arrays.asList("x2", "x4"));
//        queries.add(Arrays.asList("e", "e"));
//        queries.add(Arrays.asList("x", "x"));
        System.out.println(Arrays.toString(new CalcEquation().calcEquation(equations, values, queries)));
    }

    /**
     * 记录每个除数，被除数计算出来的值。构造出一棵树
     * 然后使用dfs计算结果
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> varMap = new HashMap<>();
        // 计算每个算式的值，比如已知a/b=2，那么就要记录a/b=2和b/a=0.5两个
        for (int i = 0; i < equations.size(); i++) {
            String key = equations.get(i).get(0);
            String value = equations.get(i).get(1);
            Map<String, Double> value1 = varMap.get(key);
            if (value1 == null) {
                value1 = new HashMap<>();
                value1.put(value, values[i]);
                varMap.put(key, value1);
            } else {
                value1.put(value, values[i]);
            }
            Map<String, Double> value2 = varMap.get(value);
            if (value2 == null) {
                value2 = new HashMap<>();
                value2.put(key, 1.0 / values[i]);
                varMap.put(value, value2);
            } else {
                value2.put(key, 1.0 / values[i]);
            }
        }
        double[] result = new double[queries.size()];
        // 遍历计算每一个待求值
        for (int i = 0; i < queries.size(); i++) {
            List<String> list = queries.get(i);
            String dividend = list.get(0);
            String divisor = list.get(1);
            // 除数被除数等于的情况特殊处理
            if (dividend.equals(divisor)) {
                result[i] = varMap.containsKey(dividend) ? 1.0 : -1.0;
                continue;
            }
            //记录已经扫描过的值，避免死循环
            Set<String> temped = new HashSet<>();
            temped.add(dividend);
            // 计算结果
            double r = dfs(varMap, dividend, divisor, temped, 1.0);
            result[i] = r;
        }
        return result;
    }

    public double dfs(Map<String, Map<String, Double>> varMap, String dividend, String divisor, Set<String> temped, Double d) {
        if (varMap.containsKey(dividend)) {
            Map<String, Double> dividends = varMap.get(dividend);
            if (dividends.containsKey(divisor)) {
                return d * dividends.get(divisor);
            } else {
                double a = -1.0;
                for (String s : dividends.keySet()) {
                    if (temped.contains(s)) continue;
                    temped.add(s);
                    double t = dfs(varMap, s, divisor, temped, d * dividends.get(s));
                    if (t != -1.0) {
                        a = t;
                        break;
                    }
                }
                return a;
            }
        } else {
            return -1.0;
        }
    }


    /**
     * TODO: 未完成 取leetCode查看该题，该题官方题解下有很多并查集的链接题目
     * 并查集
     * 上面解法实际上已经采用了并查集的部分思想，将能够以x为被除数的所有算数全部放到同一个key下
     *
     */
    public double[] calcEquation2(List<List<String>> equations, double[] values, List<List<String>> queries){
        return null;
    }

    class UnionFind {
        String[] parents;
        double[] data;

        public UnionFind(char[][] grid) {

        }

        public int find(int index) {
            return 0;
        }

        public void union(int x, int y) {

        }

    }
}
