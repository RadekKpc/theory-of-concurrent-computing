package zad3;

import java.util.HashMap;

public class Main {

    private static HashMap<Integer,Integer> histogram = new HashMap<>();

    private static int testThreads() throws InterruptedException {

        ThreadManager threadManager = new ThreadManager();
        DecThread decThread = new DecThread(threadManager);
        IncThread incThread = new IncThread(threadManager);

        threadManager.setDecThread(decThread);
        threadManager.setIncThread(incThread);

        decThread.start();
        incThread.start();
        threadManager.start();

        decThread.join();
        incThread.join();
        threadManager.join();

        return threadManager.getCounter().counter;
    }
    public static void main(String[] args) throws InterruptedException {

        for(int i=0;i<1000;i++){
            int counter = testThreads();
            if(histogram.containsKey(counter)){
                int currentValue = histogram.get(counter);
                histogram.put(counter,currentValue+1);
            }
            else{
                histogram.put(counter,1);
            }
        }

        histogram.forEach((k,v) -> System.out.println(k + ": " + v));
    }
}
