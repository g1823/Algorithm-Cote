package leetCode.moderately;

/**
 * @author: gj
 * @description: 165. 比较版本号
 */
public class CompareVersion {

    /**
     * 字符串分割
     * 时间复杂度：O(max(m,n))
     * 空间复杂度：O(max(m,n))
     */
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        for (int i = 0; i < Math.max(v1.length, v2.length); i++) {
            int a = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int b = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 双指针：
     * 也不算真正双指针
     * 在方法compareVersion中，需要O(max(m,n))的空间存储拆分后的版本号，我们可以在拆分版本号的同时进行比较
     */
    public int compareVersion2(String version1, String version2) {
        int i = 0, j = 0;
        int m = version1.length(), n = version2.length();
        while (i < m || j < n) {
            int a = 0;
            while (i < m && version1.charAt(i) != '.') {
                a = a * 10 + version1.charAt(i) - '0';
                i++;
            }
            // 跳过'.'
            i++;
            int b = 0;
            while (j < n && version2.charAt(j) != '.') {
                b = b * 10 + version2.charAt(j) - '0';
                j++;
            }
            j++;
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }
        return 0;
    }

}
