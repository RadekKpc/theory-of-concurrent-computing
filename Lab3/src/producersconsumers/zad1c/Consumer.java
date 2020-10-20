package producersconsumers.zad1c;

class Consumer extends Thread {
	private Buffer _buf;

	public Consumer(Buffer buffer){
		super();
		this._buf = buffer;
	}

	public void run() {
	  for (int i = 0; i < 100; ++i) {
		  try {
			  sleep(2000);
		  } catch (InterruptedException e) {
			  e.printStackTrace();
		  }
		  System.out.println(_buf.get());
	  }
	}
}
