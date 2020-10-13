package zad1;

public class Semaphore {
    private boolean state = true;
    private int waitingThreads = 0;

    public Semaphore() {
    }

    public synchronized void P() {
        if(!state){
            waitingThreads++;
            while(!this.state) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            waitingThreads--;
        }
        this.state = false;
    }

    public synchronized void V() {
        this.state = true;
        if(waitingThreads > 0) {
            this.notify();
        }
    }
}
