package producersconsumers.acitveObject;

import java.util.concurrent.Future;

public class BufferPutRequest extends BufferMethodRequest<Void>{

    private final int value;

    public BufferPutRequest(Buffer buffer, FutureResult<Void> future, int value) {
        super(buffer, future);
        this.value = value;
    }

    @Override
    public boolean guard(){
        return buffer.canWrite();
    }

    @Override
    public void call() {
        buffer.put(value);
        future.resolve();
    }

}
