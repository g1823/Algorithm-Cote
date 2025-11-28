package leetCode.moderately;

/**
 * @author: gj
 * @description: 6. Z 字形变换
 */
public class ZConvert {
    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        // PAHNAPLSIIGYIR
        /**
         * P   A   H   N
         * A P L S I I G
         * Y   I   R
         */
        System.out.println(new ZConvert().convert(s, 3));
    }

    /**
     * 下标对应转换
     * 理论上来说，原数组和转换后的数组每个字符是一一对应的，也就是存在一个公式，可以计算出两个字符串下标对应关系：
     * 分析：
     * 1、根据Z字结构分析，可以发现，Z字有个规律，每一组为 ：一个每一行都有值的竖线 和 一列只有一行有值的斜线。即一组有numRows + numRows - 2个字符
     * 2、按行分析，可以发现，第一行和最后一行元素的个数一致，为总共多少组就有几个（暂时不讨论有多余元素的情况）；而中间行元素个数要比首尾行多一倍
     * 3、因此，转换坐标时，需要按所处行特殊处理。
     * 步骤：
     * 1、先提前计算Z字每一行有几个元素，并计算前缀和，快速定位转换结果下标所处行
     * 2、根据Z字规律，定位到处于哪一列
     * 3、然后逆向转换为原字符串下标
     */
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        char[] chars = s.toCharArray();
        char[] res = new char[s.length()];

        // 预处理每行的字符数和前缀和
        PrecomputeData data = precompute(numRows, s.length());

        for (int i = 0; i < chars.length; i++) {
            int index = getIndex(i, numRows, chars.length, data);
            res[i] = chars[index];
        }
        return new String(res);
    }

    /**
     * 存储预处理结果
     */
    static class PrecomputeData {
        int[] count;   // 每行字符数
        int[] prefix;  // 前缀和
        int cycle;
    }

    /**
     * 预处理：计算每行有多少个字符，以及前缀和
     */
    private PrecomputeData precompute(int numRows, int n) {
        PrecomputeData data = new PrecomputeData();
        data.count = new int[numRows];
        // prefix[0] = 0
        data.prefix = new int[numRows + 1];
        // 每个循环，也就是每组有多少字符
        int cycle = 2 * numRows - 2;
        data.cycle = cycle;
        // 总共有几组数据
        int fullCycles = n / cycle;
        // 完整组数结束后剩余字符数量
        int rem = n % cycle;

        for (int r = 0; r < numRows; r++) {
            if (r == 0 || r == numRows - 1) {
                // 第一行和最后一行就是完整组数+最后剩余数量是否能达到当前行
                data.count[r] = fullCycles + (rem > r ? 1 : 0);
            } else {
                // 完整组数里，中间行每行一组两个元素
                int c = fullCycles * 2;
                // 剩余字符数大于当前行，那么竖直列会多出一个元素
                if (rem > r) {
                    // 竖直部分
                    c += 1;
                }
                // 剩余部分字符数大于一组完整字符数-当前行，那么斜线列会多出一个元素
                if (rem > cycle - r) {
                    // 斜线部分
                    c += 1;
                }
                data.count[r] = c;
            }
            // 计算前缀和，即当前行及当前行前面的行共有多少个元素
            data.prefix[r + 1] = data.prefix[r] + data.count[r];
        }

        return data;
    }

    /**
     * 将结果字符串下标 resIdx 映射为原字符串下标
     */
    public static int getIndex(int resIdx, int numRows, int n, PrecomputeData data) {
        // 特殊情况：只有 1 行
        if (numRows == 1) {
            return resIdx;
        }

        int cycle = data.cycle;

        // Step1: 找到属于哪一行（利用前缀和直接定位）：
        // 由于resIdx是转换后Z型按从上往下，从左往右的顺序读取，因此可以直接采用前n行前缀和来计算
        int row = 0;
        while (row < numRows && resIdx >= data.prefix[row + 1]) {
            row++;
        }
        // 处于当前行的第几列
        int k = resIdx - data.prefix[row];

        // Step2: 还原原字符串下标
        if (row == 0) {
            // 处于第一行，正好是上一个循环（上一组完整数据）的结束后第一个元素，因此直接用k*一组循环数就可以得到原始下标
            return k * cycle;
        }
        if (row == numRows - 1) {
            // 处于最后一行，则正好是上一个循环（上一组完整数据）的结束后的下一个循环竖直列最后一个元素，因此直接用k*一组循环数+行数就可以得到原始下标
            return k * cycle + row;
        }
        // 一个完整循环内，非首尾行内一定有且只有两个字符，因此可以使用%2取余确定是竖直列的还是斜线的
        if (k % 2 == 0) {
            // 竖直
            return (k / 2) * cycle + row;
        } else {
            // 斜线
            return (k / 2) * cycle + (cycle - row);
        }
    }


    /**
     * 直接模拟转换Z字，使用numRows个StringBuilder数组，每个StringBuilder表示转换后的Z字某一行
     * 先模拟转换Z字，将StringBuilder数组填满，然后从第一个往后拼接即可。
     */
    public String convert2(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        int row = 0, step = 1;
        for (char c : s.toCharArray()) {
            rows[row].append(c);
            // 到顶或到底，改变方向
            if (row == 0) {
                step = 1;
            } else if (row == numRows - 1) {
                step = -1;
            }
            row += step;
        }

        StringBuilder res = new StringBuilder();
        for (StringBuilder sb : rows) {
            res.append(sb);
        }
        return res.toString();
    }
}
