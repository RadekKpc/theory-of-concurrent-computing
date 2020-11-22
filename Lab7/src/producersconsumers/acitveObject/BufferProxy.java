package producersconsumers.acitveObject;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class BufferProxy {

    BufferScheduler scheduler;
    Buffer buffer;

    public BufferProxy(int size) {
        buffer = new Buffer(size);
        scheduler = new BufferScheduler(buffer);
        scheduler.start();
    }

    FutureResult<Integer> get(){
        FutureResult<Integer> result = new FutureResult<>();
        BufferGetRequest method = new BufferGetRequest(buffer,result);
        scheduler.enqueue(method);
        return result;
    }

    FutureResult<Void> put(int value){
        FutureResult<Void> result = new FutureResult<>();
        BufferPutRequest method = new BufferPutRequest(buffer,result,value);
        scheduler.enqueue(method);
        return result;
    }
}
