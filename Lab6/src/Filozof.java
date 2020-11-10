class Filozof extends Thread {
  private int _licznik = 0;

  public void run() {
	while (true) {

	  // jedzenie
	  ++_licznik;
	  if (_licznik % 100 == 0) {
		System.out.println("Filozof: " + Thread.currentThread() +
			"jadlem " + _licznik + " razy");
	  }
	  // koniec jedzenia

	}
  }
}
