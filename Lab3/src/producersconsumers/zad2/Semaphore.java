package producersconsumers.zad2;

public class Semaphore {
    private boolean state;
    private int waitingThreads = 0;

    public Semaphore(boolean state) {
        this.state = state;
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
