package producersconsumers.zad2;

class Consumer extends Thread {
	private final Buffer _buf;

	public Consumer(Buffer buffer){
		super();
		this._buf = buffer;
	}

	public void run() {
	  for (int i = 0; i < 100; ++i) {

		  System.out.println(_buf.get());

	  }
	}
}
