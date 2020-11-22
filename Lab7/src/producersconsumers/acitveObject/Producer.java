package producersconsumers.acitveObject;

class Producer extends Thread {
    private BufferProxy _buf;

    public Producer(BufferProxy buffer){
        super();
        this._buf = buffer;
    }

    public void run() {

        for (int i = 0; i < 100; ++i) {
            _buf.put(i);
            System.out.println("I add the " + i);
        }
    }
}
