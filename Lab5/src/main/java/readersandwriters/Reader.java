package readersandwriters;

import java.util.Random;

public class Reader {

    private final IBuffer _buf;
    private final int operations;
    private final long sleepTime;
    public Reader(IBuffer buffer, int operations,long sleepTime){
        super();
        this._buf = buffer;
        this.operations = operations;
        this.sleepTime = sleepTime;
    }

    public void run() {

		Random random = new Random();

        for (int i = 0; i < operations; i++) {
            _buf.get(random.nextInt(_buf.getSize()));
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
