package producersconsumers.zad1;

class Producer extends Thread {
    private Buffer _buf;

    public Producer(Buffer buffer){
        super();
        this._buf = buffer;
    }

    public void run() {

        for (int i = 0; i < 100; ++i) {

            _buf.put(i);
        }
    }
            }
