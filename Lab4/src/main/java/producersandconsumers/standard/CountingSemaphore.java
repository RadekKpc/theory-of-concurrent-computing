package producersandconsumers.standard;

public class CountingSemaphore {
    private int resources = 0;

    public CountingSemaphore(int resources) {
        this.resources = resources;
    }

    public synchronized void release() {
        this.resources++;
        this.notify();
    }

    public synchronized void acquire() throws InterruptedException{
        while(this.resources <= 0) wait();
        this.resources--;
    }

}
