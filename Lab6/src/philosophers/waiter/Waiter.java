package philosophers.waiter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Waiter {

    ArrayList<Fork> forks = new ArrayList<>();
    Semaphore waiterSemaphore = new Semaphore(1);
    public void addForks(Fork[] fork){
        forks.addAll(Arrays.asList(fork));
    }


    public void askAboutForks(Fork leftFork, Fork rightFork){
        try {
            waiterSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        


        waiterSemaphore.release();
    }
}
