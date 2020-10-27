package producersandconsumers;

import java.util.Random;

class Producer extends Thread {
    private IBuffer _buf;
    private int operations;

    public Producer(IBuffer buffer, int operations){
        super();
        this._buf = buffer;
        this.operations = operations;
    }

    public void run() {

//        Random random = new Random();
//        int operations = random.nextInt(this.operations);

        for (int i = 0; i < operations; i++) {
            _buf.put(i);
        }
    }
}
