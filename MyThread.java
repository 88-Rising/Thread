import java.util.Scanner;

public class Test {

    public static void main(String[] args)  {
        Scanner sc=new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(n);
         /*
         * 继承Thread类
         * */
         MyThread t=new MyThread();
         t.start();        //是一个真实启动的线程的方法
         //t.run();      //不是真实启动一个线程的方法 只是实现了部分代码
         //实现Runnable类
        Runnable runner =new MyRunner();   //向上转型
         Thread t2=new Thread(runner);  //Runnable tmp = runner;隐藏了这一步操作
         t2.start();
         //Java main 是Java层面的主线程 和main方法中自行创建的线程是同级的

    }
}
class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("MyThread");
        while(true){

        }
    }

}
class MyRunner implements Runnable{
    @Override
    public void run() {
        System.out.println("MyRunner");


    }
}
