public class MySequence {
    public static void main(String[] args) {
//       final Object obj=new Object();
        Thread a=new Thread(new Runnable() {
            @Override
            public void run() {
                for(char i='A';i<='Z';++i){
                    synchronized (MySequence.class){
                        try {
                            MySequence.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(i);
                        MySequence.class.notify();
                    }
                }
            }
        });

        Thread b=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<=52;++j){
                    synchronized(MySequence.class){
                        System.out.println(j);
                        System.out.println(++j);
                        MySequence.class.notify();
                        try {
                            MySequence.class.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        a.start();
        b.start();
    }
}

