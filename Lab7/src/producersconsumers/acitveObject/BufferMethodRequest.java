package producersconsumers.acitveObject;

import java.util.concurrent.Future;

public abstract class BufferMethodRequest<R> {

    Buffer buffer;
    FutureResult<R> future;

    public BufferMethodRequest(Buffer buffer,FutureResult<R> future) {
        this.buffer = buffer;
        this.future = future;
    }

    abstract boolean guard();
    abstract void call();

}
