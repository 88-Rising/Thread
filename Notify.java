/*
* 1、wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。
2、wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，即，一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。
3、 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况。所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程让其获得锁
4、wait() 需要被try catch包围，以便发生异常中断也可以使wait等待的线程唤醒。
5、notify 和wait 的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
6、notify 和 notifyAll的区别
notify方法只唤醒一个等待（对象的）线程并使该线程开始执行。所以如果有多个线程等待一个对象，这个方法只会唤醒其中一个线程，选择哪个线程取决于操作系统对多线程管理的实现。notifyAll 会唤醒所有等待(对象的)线程，尽管哪一个线程将会第一个处理取决于操作系统的实现。如果当前情况下有多个线程需要被唤醒，推荐使用notifyAll 方法。比如在生产者-消费者里面的使用，每次都需要唤醒所有的消费者或是生产者，以判断程序是否可以继续往下执行。
7、在多线程中要测试某个条件的变化，使用if 还是while？
　　要注意，notify唤醒沉睡的线程后，线程会接着上次的执行继续往下执行。所以在进行条件判断时候，可以先把 wait 语句忽略不计来进行考虑；显然，要确保程序一定要执行，并且要保证程序直到满足一定的条件再执行，要使用while进行等待，直到满足条件才继续往下执行。如下代码：
* */

import lesson3.T;

/*
* 线程之间的通信
* 其实wait()方法就是使线程停止运行。
* 1. 方法wait()的作用是使当前执行代码的线程进行等待，wait()方法是Object类的方法，该方法是用来将当前线程
* 置入“预执行队列”中，并且在wait()所在的代码处停止执行，直到接到通知或被中断为止。
* 2. wait()方法只能在同步方法中或同步块中调用。如果调用wait()时，没有持有适当的锁，会抛出异常。
* 3. wait()方法执行后，当前线程释放锁，其他线程就会竞争重新获取锁
*
* notify（）的含义是：唤醒调用对象的锁，
* */
public class Notify {
    private static volatile int COUNT;

    public synchronized static void produce() {  //对当前类进行加锁 这样是不行的 要给当前对象加锁
        COUNT += 3;

    }

    public synchronized static void consume() {
        COUNT--;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {  //生产者
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int j=0;j<10;j++){
                            synchronized (Notify.class){

                                if (COUNT + 3 > 100) {//如果库存满了则进行等待
                                  Notify.class.wait();
                                }
                                produce();
                                System.out.println(Thread.currentThread().getName()+"生产，库存总量："+COUNT);
                                Thread.sleep(500);
                                Notify.class.notifyAll(); //唤醒锁

                            }
                            Thread.sleep(500);


                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }).start();
    }
        for (int i = 0; i < 3; i++) { //消费者
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            synchronized (Notify.class) {
                                if (COUNT == 0) {   //如果库存为0 则进入等待
                                    Notify.class.wait();
                                }
                                consume();
                                System.out.println(Thread.currentThread().getName() + "消费，库存总量：" + COUNT);
                                Thread.sleep(500);
                                Notify.class.notifyAll();  //唤醒锁
                            }
                            Thread.sleep(500);
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

}
