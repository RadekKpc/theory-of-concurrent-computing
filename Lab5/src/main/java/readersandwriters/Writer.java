package readersandwriters;

import java.util.Random;

public class Writer extends Thread {

    private IBuffer _buf;
    private int operations;
    private long sleepTime;

    public Writer(IBuffer buffer, int operations,long sleepTime){
        super();
        this._buf = buffer;
        this.operations = operations;
        this.sleepTime = sleepTime;
    }

    public void run() {

        Random random = new Random();

        for (int i = 0; i < operations; i++) {
            int toWrite = random.nextInt(10000);
            int randomIndex = random.nextInt(_buf.getSize());
            _buf.put(toWrite,randomIndex);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
