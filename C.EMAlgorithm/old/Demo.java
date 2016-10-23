public class Demo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 5; i++){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Thread T1 : "+i);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Thread T2 : "+i);
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 5; i++){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Thread T3 : "+i);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}