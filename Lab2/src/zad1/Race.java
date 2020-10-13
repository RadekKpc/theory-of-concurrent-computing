package zad1;// zad1.Race.java
// Wyscig

import java.util.HashMap;

class Counter {
    private Semaphore semaphore;
    private int _val;
    public Counter(int n) {

        _val = n;
        semaphore = new Semaphore();
    }
    public void inc() {
        semaphore.P();
        _val++;
        semaphore.V();
    }
    public void dec() {
        semaphore.P();
        _val--;
        semaphore.V();
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

class Race {
    private static HashMap<Integer,Integer> histogram = new HashMap<>();

    private static int testThreads() throws InterruptedException {
        Counter cnt = new Counter(0);

        IThread iThread = new IThread(cnt);
        DThread dThread = new DThread(cnt);

        iThread.start();
        dThread.start();

        iThread.join();
        dThread.join();

        return cnt.value();
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<100;i++){
            int counter = testThreads();
            if(histogram.containsKey(counter)){
                int currentValue = histogram.get(counter);
                histogram.put(counter,currentValue+1);
            }
            else{
                histogram.put(counter,1);
            }
        }

        histogram.forEach((k,v) -> System.out.println(k + ": " + v));
    }
}

