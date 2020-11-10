package philosophers.waiter;

import java.util.Arrays;
import java.util.List;

public class Problem {

    public static void main(String[] args) {
        final int dinnerSize = 10;
        final int eatTimeMs = 10;
        final int thinkTImeMs = 100;

        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        Waiter waiter = new Waiter();
        waiter.addForks(new Fork[] {fork1,fork2,fork3,fork4,fork5});

        Philosopher phil1 = new Philosopher(fork1,fork2,"First",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[36m",waiter);
        Philosopher phil2 = new Philosopher(fork2,fork3,"Second",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[34m",waiter);
        Philosopher phil3 = new Philosopher(fork3,fork4,"Third",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[37m",waiter);
        Philosopher phil4 = new Philosopher(fork4,fork5,"Fourth",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[32m",waiter);
        Philosopher phil5 = new Philosopher(fork5,fork1,"Fifth",dinnerSize,thinkTImeMs,eatTimeMs,"\u001B[31m",waiter);

        List<Thread> threads = Arrays.asList(phil1,phil2,phil3,phil4,phil5);

        long time = measureTime(threads);
        System.out.println(time/1000000 + "ms");
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
