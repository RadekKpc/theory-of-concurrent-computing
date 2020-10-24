package pipelining;

class Consumer extends Thread {
	private final Buffer _buf;
	private final int number;
	public Consumer(Buffer buffer,int number){
		super();
		this._buf = buffer;
		this.number =number;
	}

	public void run() {
	  for (int i = 0; i < 100; ++i) {

		  System.out.println(_buf.get(number,0));

	  }
	}
}
