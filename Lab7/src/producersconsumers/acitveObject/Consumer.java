package producersconsumers.acitveObject;

class Consumer extends Thread {
	private BufferProxy _buf;

	public Consumer(BufferProxy buffer){
		super();
		this._buf = buffer;
	}

	public void run() {
	  for (int i = 0; i < 100; ++i) {
		  System.out.println("I get the: " + _buf.get().get());
	  }
	}
}
