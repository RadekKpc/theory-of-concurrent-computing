package producersandconsumers;

import producersandconsumers.library.LibBuffer;
import producersandconsumers.standard.StandardBuffer;

import java.util.ArrayList;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) {
        System.out.println("Compare java monitors vs concurrency utilities");
        final int M = 30;
        final int produceNumber = 10;
        long[][] libTimes = new long[M][M];
        long[][] standardTimes = new long[M][M];

        for(int producers = 1; producers <= M ; producers++){
            for(int consumers = 1; consumers <= M; consumers++){

                ArrayList<Thread> libThreads = new ArrayList<>();
                ArrayList<Thread> standardThreads = new ArrayList<>();

                LibBuffer libBuffer = new LibBuffer(M);
                StandardBuffer standardBuffer = new StandardBuffer(M);

//                rozdzielenie rpacy pomiędzy wątki, tyle ile producenci wyprodukują konsumęci musza przetworzyć
                int[] countOfConsumersOperations = new int[consumers];
                for(int i =0;i<consumers;i++){
                    countOfConsumersOperations[i] = 0;
                }
                int poolOfOperations = producers*produceNumber;

                while (poolOfOperations != 0){
                    countOfConsumersOperations[poolOfOperations%consumers]++;
                    poolOfOperations --;
                }

//                add threads into lts
                for(int i=0;i<producers ;i++){
                    libThreads.add(new Producer(libBuffer, produceNumber));
                    standardThreads.add(new Producer(standardBuffer, produceNumber));
                }

                for(int i=0;i<consumers ;i++){
                    libThreads.add(new Consumer(libBuffer, countOfConsumersOperations[i]));
                    standardThreads.add(new Consumer(standardBuffer, countOfConsumersOperations[i]));
                }

//              calculate time

                libTimes[producers-1][consumers-1] = measureTime(libThreads);
                standardTimes[producers-1][consumers-1] = measureTime(standardThreads);
            }
        }

        printResult(standardTimes,M);
        System.out.println();
        printResult(libTimes,M);
    }

    private static void printResult(long[][] table, int M){
        for(int i=0;i<M;i++){
            System.out.println();
            for(int j =0;j<M;j++){
                System.out.print(table[i][j]);
                System.out.print(" ");
            }
        }
    }


    private static long measureTime(ArrayList<Thread> threads){
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
