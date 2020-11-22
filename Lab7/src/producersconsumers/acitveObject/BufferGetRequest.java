package producersconsumers.acitveObject;

import java.util.concurrent.Future;

public class BufferGetRequest extends BufferMethodRequest<Integer> {

    public BufferGetRequest(Buffer buffer, FutureResult<Integer> future) {
        super(buffer, future);
    }

    @Override
    public boolean guard() {
        return buffer.canRead();
    }

    @Override
    public void call() {
        int result =  buffer.get();
        future.setResult(result);
        future.resolve();
    }
}
