class KMP {
    //next用来存储next数组，pat为子串，txt为待匹配字符串
    int next[];
    String pat;
    String txt;
    public KMP(String pat,String txt){
        this.next = new int[pat.length()];
        this.pat = pat;
        this.txt = txt;
    }
    public int KMP(){
        if(txt.length() == 0||pat.length() == 0){
            return -1;
        }
        getNext();
        //i表示txt下标，j表示pat下标
        int i = 0, j = 0;
        //如果j等于了pat.length()-1，说明匹配完毕,i等于了txt.length()-1，说明待匹配字符串匹配到末尾了。
        while(i < txt.length() && j < pat.length()){
            //若i和j位置字符相同，说明匹配，则继续向后匹配
            if(pat.charAt(j++) == txt.charAt(i++)){
                i++;
                j++;
            }else{
                //不相等则先判断是否等于0，若大于0可以从next数组中滑动
                if(j > 0){
                    j = next[j-1];
                }else{
                    //若j等于0，说明从第一个就匹配失败了，则i++。
                    i++;
                }
            }
        }
        return j == pat.length()? i-j : -1;
    }
    public void getNext(){
        //先做初始化,i表示当前最大长度，j表示当前数组下标
        next[0] = 0;
        int i = 0,j = 1;
        while(j < pat.length()){
            //若next[i] == next[j],说明前缀与后缀相同，那么比较前缀和后缀下一个字母，即i++和j++。
            if(pat.charAt(i) == pat.charAt(j)){
                next[j] = ++i;
                j++;
            }else{
                //若不相等，未避免越界，先判断是否等于0，等于0直接该位置最大匹配长度为0，j+1
                //现在变为如何求j位置的最大前后缀，从j-i到j不匹配，那么可能存在短一点的前后缀，在j-i到j之间。
                //可以循环暴力求解，但是注意到 0到i-1 与 j-i到j-1 是完全相同的
                //意味着 0到i-1 与 j-i到j-1 的后缀是相同的。
                //想要找j位置的最长匹配前后缀，可以先找到它前面的最长前后缀，然后再判断它和下一个字符是否相同
                //而0到i-1位置的之前已经找过，所以直接拿来用即可
                if(i == 0) {
                    next[j++] = 0;
                }else {
                    i = next[i - 1];
                }
            }
        }
    }
}
