package philosophers.waiter;

import java.util.concurrent.Semaphore;

public class Waiter {

    Semaphore platesSemaphore;

    public Waiter(int philosophersAmount) {
        this.platesSemaphore = new Semaphore(philosophersAmount - 1);
    }

    public void askAboutPlate(){
        try {
            platesSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void realisePlate(){
        platesSemaphore.release();
    }
}
