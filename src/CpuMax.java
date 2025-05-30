public class CpuMax {
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < cores; i++) {
            new Thread(() -> {
                int j = 0;
                while (true) {
                    // 每个线程运行一个死循环
                    Math.random();
                    j++;
                    if(j>100000000){
                        break;
                    }
                }
            }).start();
        }
    }
}