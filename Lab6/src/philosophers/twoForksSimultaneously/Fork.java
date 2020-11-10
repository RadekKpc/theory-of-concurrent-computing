package philosophers.twoForksSimultaneously;

import java.util.concurrent.Semaphore;

public class Fork {

    Semaphore semaphore = new Semaphore(1);

    public Fork(){}

    public void put(){
        semaphore.release();
    }

    public boolean take(){
         return semaphore.tryAcquire();
    }
}
