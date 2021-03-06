package philosophers.waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class WaiterPhilosophers implements Callable<Long> {
     int dinnerSize;
     int eatTimeMs;
     int thinkTImeMs;

    public WaiterPhilosophers(int dinnerSize, int eatTimeMs, int thinkTImeMs) {
        this.dinnerSize = dinnerSize;
        this.eatTimeMs = eatTimeMs;
        this.thinkTImeMs = thinkTImeMs;
    }

    public Long call() throws Exception{

        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        Waiter waiter = new Waiter(5);

        Philosopher phil1 = new Philosopher(fork1,fork2,"First",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[36m",waiter);
        Philosopher phil2 = new Philosopher(fork2,fork3,"Second",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[34m",waiter);
        Philosopher phil3 = new Philosopher(fork3,fork4,"Third",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[37m",waiter);
        Philosopher phil4 = new Philosopher(fork4,fork5,"Fourth",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[32m",waiter);
        Philosopher phil5 = new Philosopher(fork5,fork1,"Fifth",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[31m",waiter);

        List<Thread> threads = new ArrayList<Thread>();
        threads.add(phil1);
        threads.add(phil2);
        threads.add(phil3);
        threads.add(phil4);
        threads.add(phil5);

        return measureTime(threads);
    }

    private static long measureTime(List<Thread> threads){
        long startTime = System.nanoTime();

        for( Thread thread: threads){
            thread.start();
        }

        for( Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return System.nanoTime() - startTime;
    }

}
