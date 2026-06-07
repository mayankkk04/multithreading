package Threads;

public class Threads extends Thread{
    @Override
    public void run(){
        for(int i = 0 ; i <= 5; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Threads t1 = new Threads();
//        System.out.println(t1.getState());
        t1.start();
    }
}
