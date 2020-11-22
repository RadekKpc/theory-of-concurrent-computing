package producersconsumers.acitveObject;

import java.util.concurrent.Semaphore;

public class FutureResult<R> {

    private R result;
    Semaphore isDone = new Semaphore(0);

    public void setResult(R result) {
        this.result = result;
    }

    public R get(){

        try {
            isDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void resolve(){
        isDone.release(1);
    }


}
