package pipelining;

import java.util.ArrayList;
import java.util.Arrays;

public class TestPipline {
    public static void main(String[] args) {

//       We numerate processes from producer to consumer
        int countOfProcessesInPipeline = 10;
//       init buffer for 12 threads
        Buffer buffer = new Buffer(15,10 + 2,0);

        ArrayList<Thread> threads = new ArrayList<>();

//        create Producer
        threads.add(new Producer(buffer,0));
//        create threads between producer and consumer
        for(int i=0;i<countOfProcessesInPipeline; i++){
            threads.add(new ProcessBetweenProducerAndConsumer(buffer,i+1));
        }
//        create consumer
        threads.add(new Consumer(buffer,countOfProcessesInPipeline + 1));

//        run pipeline
        for(Thread thread: threads){
            thread.start();
        }
////        wait for threads
        for(Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        At the end of the buffer we should see
//        10 to 109, because each thread add 1 to the number

        System.out.println(Arrays.toString(buffer.buffer));
    }
}
