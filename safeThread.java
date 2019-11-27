public class safeThread {
    public static int COUNT;
    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            final int j = i;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 10000; k++) {
//                        COUNT++;
                        increment();
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

    }
    //重点理解 锁的是那个对象
    //静态同步方法 锁定的是当前类对象
    //实例方法 锁定当前对象（this）
    //同步代码块 锁定的是obj对象

    //锁定对象的所有同步的地方 都会同步互斥
    /*
    * public class  LockObject
    *
    * synchronized nethodA
    * synchronized static methodB
    *
    * methodC {
    *       synchronized(LockObject class){
    * }
    * }
    * methodD{
    *     synchronized(this,class){}
    * }
    * */
//    public synchronized static void increment(){ //这个方法是一个同步的方法 使用这个方法 线程安全的方法就得以解决
//  //synchronized  关键字 监视器锁    相当于对对象进行了一个加锁如果一个线程正在运行这个同步方法 那么其他线程将会等待
//    //其他线程执行到这个代码 就不会执行而是会等待 的前一个线程执行完之后才会轮到下一个
//        //总的来说就是 一个轮流执行的特性  不是每一个线程可以一起执行
//        COUNT++;
//    }
    //上下两个相同
    public static void increment(){
        synchronized (safeThread.class){
            COUNT++;
        }

    }
    public synchronized static  void decrement(){
        COUNT--;
    }
//    public  synchronized static void increment2(){
//        COUNT++;
//    }
    //上下这两个 意思相同
    public void  increment2(){
        synchronized (this){
            COUNT++;
        }
    }


}
