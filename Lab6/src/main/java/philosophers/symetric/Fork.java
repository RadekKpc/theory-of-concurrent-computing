package philosophers.symetric;

import java.util.concurrent.Semaphore;

public class Fork {

    Semaphore semaphore = new Semaphore(1);

    public Fork(){}

    public void put(){
        semaphore.release();
    }

    public void take(){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
