package readersandwriters;

import java.util.Random;

public class Writer extends Thread {

    private final IBuffer _buf;
    private final int operations;

    public Writer(IBuffer buffer, int operations){
        super();
        this._buf = buffer;
        this.operations = operations;
    }

    public void run() {

        Random random = new Random();

        for (int i = 0; i < operations; i++) {
            int toWrite = random.nextInt(10000);
            int randomIndex = random.nextInt(_buf.getSize());
            _buf.put(toWrite,randomIndex);
        }
    }

}
