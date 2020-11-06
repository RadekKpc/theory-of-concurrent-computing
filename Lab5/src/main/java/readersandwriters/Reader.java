package readersandwriters;

import java.util.Random;

public class Reader extends Thread{

    private final IBuffer _buf;
    private final int operations;
    public Reader(IBuffer buffer, int operations){
        super();
        this._buf = buffer;
        this.operations = operations;
    }

    public void run() {

		Random random = new Random();

        for (int i = 0; i < operations; i++) {
            _buf.get(random.nextInt(_buf.getSize()));
        }
    }

}
