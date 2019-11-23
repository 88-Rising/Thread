线程不安全的三大特性：
原子性 ： 在某个环境下 不可被分割（包括被中断 造成结果不正确等的操作 ）
*      对于共享变量 基本数据类型赋值操作是原子性 但不包括64位的 long/double操作
*         经典的非原子操作的单条指令是： n++  /n-- /new object
* 可见性 ： 工作内存和主内存的一致性（对于每个线程来说 这个给共享变量也是不可见的）
* 有序性 ： 执行下列的东西 可能是无序执行
*
*/
/*
* DataSource  ds= new MysqlDataSource();
*  1.分配该对象内存空间
*  2.对这个对象进行初始化操作
*  3.把这个对象给ds引用
*
* */
public class UnsafeThread {
    public static int COUNT;//初始化值是0

    public static List<Integer> list=new ArrayList<>();

    //开启20个线程 每个线程对COUNT进行++操作10000次  预期结果200000
    //堆里边主要放对象
    public static void main(String[] args) {
        String s1= "abc";  //运行时常量池  创建了一个对象
        String s2=new String("a");//堆  创建了 两个对象
        String s3=new String("abc");//引用常量池 创建了一个对象
        //常量池中保存的东西：String int..
        //如果想共享变量 要放在堆里或者方法区里也可以是常量池

        for (int i = 0; i < 20; i++) {
            final int j = i;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 10000; k++) {
                        COUNT++;
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }

        while(Thread.activeCount()>2){  //判断现在是否还有线程在执行 如果建立的线程还没有执行完 则不使JavaMian主线程执行
            Thread.yield();  //让JavaMain先不要执行 让创建的其他线程先执行 让出时间片
            //使当前由运行态转变为就绪态
        }
        System.out.println(COUNT);
//
//        for (int i = 0; i < 20; i++) {
//            final int j = i;
//            Runnable r = new Runnable() {
//                @Override
//                public void run() {
//                    for (int k = 0; k < 10000; k++) {
////                        COUNT++;
//                        list.add(j*10000+k);//
//                    }
//                }
//            };
//        Thread t = new Thread(r);
//        t.start();
//
//        }
//
//        while(Thread.activeCount()>2){  //判断现在是否还有线程在执行 如果建立的线程还没有执行完 则不使JavaMian主线程执行
//            Thread.yield();  //让JavaMain先不要执行 让创建的其他线程先执行 让出时间片
//            //使当前由运行态转变为就绪态
//        }
//        System.out.println(list.size());


    }
}
