package producersandconsumers;

import java.util.Random;

class Consumer extends Thread {
	private final IBuffer _buf;
	private final int operations;
	public Consumer(IBuffer buffer, int operations){
		super();
		this._buf = buffer;
		this.operations = operations;
	}

	public void run() {

//		Random random = new Random();
//		int operations = random.nextInt(M);

	  for (int i = 0; i < operations; i++) {
		  _buf.get();
	  }
	}
}
