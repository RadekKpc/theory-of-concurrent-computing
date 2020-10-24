package producersconsumers.zad1c;

class Producer extends Thread {
    private Buffer _buf;

    public Producer(Buffer buffer){
        super();
        this._buf = buffer;
    }

    public void run() {

        for (int i = 0; i < 100; ++i) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _buf.put(i);
        }
    }
}
