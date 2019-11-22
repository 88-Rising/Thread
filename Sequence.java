public class Sequence {
    public static void main(String[] args) {
        //启动0到9编号的十个线程 每个线程打印他的编号
        //表现的现象是无序的
        //进程属于分配资源的最小单位 线程是资源调度的最小单位也是CPU调度的最小单位

        //线程是由操作系统进行调度管理的 所以是无序的
        //学习某种手段让它变成有序的

        for (int i=0;i<=100;i++){
            final int j=i;
            Runnable r = new Runnable() {
                @Override
                public void run() {

                    System.out.println(j);
                }
            };
            Thread t=new Thread(r);
            t.start();

        }
    }
}
