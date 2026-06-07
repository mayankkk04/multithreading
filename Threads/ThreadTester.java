package Threads;

public class ThreadTester {
    public static void main(String[] args) {
        Threads thread = new Threads();
        Thread t1 = new Thread(thread);
        t1.start();
        for(int i = 0 ; i < 5 ; i++) System.out.println(Thread.currentThread().getName());
    }
}
