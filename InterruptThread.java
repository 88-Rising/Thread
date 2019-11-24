public class InterruptThread {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //调用Sleep方法/wait/join调用这些方法 线程进入一个阻塞状态
                    // 就算进入阻塞状态 也是可以中断的 中断之后会抛出一个异常
                    Thread.sleep(5000);
                for(int i=0;i<5;i++){
                    System.out.println(Thread.currentThread().isInterrupted());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    });
        thread.start();   //标志位 为false 抛出异常之后 标志位会重置为false
//        try {
//            thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        thread.interrupt();//调用之后直接打断就会抛出异常  标志位改为了TRUE
    //可以用来打断阻塞的线程 优势在于可以中断wait sleep join的阻塞线程



    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<10;i++){
//                    boolean tmp=标志位；
//                    标志位=false;
//                    renturn tmp;
                //作用就是 重置标志位位false 并且返回之前的标志位  做的使重置的作用
                boolean b=Thread.currentThread().interrupted();//先返回之前的标志位 在之后重置改为FALSE

                //boolean c=Thread.currentThread().isInterrupted();
                //isInterrupted:打印当前标志位 只获取不重置
                System.out.println(b);
            }
        }
    });//在线程创建了以后有一个默认的标志位=false
        thread2.start();
        thread2.interrupt();//修改标志位=true
}
}
