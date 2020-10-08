package zad1;// Race.java
// Wyscig

class Counter {
    private int _val;
    public Counter(int n) {
        _val = n;
    }
    public void inc() {
        _val++;
    }
    public void dec() {
        _val--;
    }
    public int value() {
        return _val;
    }
}

// Watek, ktory inkrementuje licznik 100.000 razy
class IThread extends Thread {

    private Counter cnt;

    public IThread(Counter cnt){
        this.cnt = cnt;
    }

    public void run() {
        for(int i =0;i<100000;i++) {
            cnt.inc();
        }
    }
}

// Watek, ktory dekrementuje licznik 100.000 razy
class DThread extends Thread {

    private Counter cnt;

    public DThread(Counter cnt){
        this.cnt = cnt;
    }

    public void run() {
        for(int i =0;i<100000;i++) {
            cnt.dec();
        }
    }
}

public class Main extends  Thread{


    public static void main(String[] args) throws InterruptedException {
        Counter cnt = new Counter(0);

        IThread iThread = new IThread(cnt);
        DThread dThread = new DThread(cnt);

        iThread.start();
        dThread.start();

        iThread.join();
        dThread.join();

        System.out.println("stan=" + cnt.value());
    }
}