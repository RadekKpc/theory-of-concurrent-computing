package producersconsumers.acitveObject;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferScheduler extends Thread {

    LinkedBlockingQueue<BufferMethodRequest<?>> activeQueue = new LinkedBlockingQueue<>();
    Buffer buffer;

    public BufferScheduler(Buffer buffer) {
        this.buffer = buffer;
    }


    public void enqueue(BufferMethodRequest<?> method){
        try {
            activeQueue.put(method);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void dispatch()   {

        try {
            BufferMethodRequest<?> method = activeQueue.take();

                if (method.guard()) {
                    method.call();
                    activeQueue.remove(method);
                    System.out.println(buffer);
                }
            else{
                activeQueue.put(method);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        System.out.println("Run Scheduler");
        while(true){
            dispatch();
        }
    }
}
