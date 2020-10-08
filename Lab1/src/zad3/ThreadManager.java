package zad3;

public class ThreadManager extends Thread {

    private DecThread decThread;
    private IncThread incThread;

    private boolean firstFisihedWork;
    private boolean secondFisihedWork;

    private Counter counter;

    public ThreadManager() {
        counter = new Counter(0);
        secondFisihedWork = false;
        firstFisihedWork = false;
    }

    public boolean isFirstFisihedWork() {
        return firstFisihedWork;
    }

    public void setFirstFisihedWork(boolean firstFisihedWork) {
        this.firstFisihedWork = firstFisihedWork;
    }

    public boolean isSecondFisihedWork() {
        return secondFisihedWork;
    }

    public void setSecondFisihedWork(boolean secondFisihedWork) {
        this.secondFisihedWork = secondFisihedWork;
    }

    public void run(){

        while(!firstFisihedWork || !secondFisihedWork){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("STAN LCIZNIKA TO: " + counter.getCounter());

    }

    Counter getCounter() {
        return counter;
    }

    public DecThread getDecThread() {
        return decThread;
    }

    void setDecThread(DecThread decThread) {
        this.decThread = decThread;
    }

    public IncThread getIncThread() {
        return incThread;
    }

    void setIncThread(IncThread incThread) {
        this.incThread = incThread;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

}
