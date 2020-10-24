package pipelining;

class ProcessBetweenProducerAndConsumer extends Thread {
	private final Buffer _buf;
	private final int number;
	public ProcessBetweenProducerAndConsumer(Buffer buffer,int number){
		super();
		this._buf = buffer;
		this.number = number;
	}

	public void run() {
	  for (int i = 0; i < 100; ++i) {
		  _buf.calculate(number,number+1);
	  }
	}
}
