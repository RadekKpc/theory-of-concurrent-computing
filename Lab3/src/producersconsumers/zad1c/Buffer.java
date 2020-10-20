package producersconsumers.zad1c;


class Buffer {

  private int[] buffer;
  private boolean[] canWrite;
  private final int size;

  public Buffer(int size){
    this.size = size;
    buffer = new int[size];
    canWrite = new boolean[size];

    for (int i =0;i < size; i++){
      buffer[i]= 0;
      canWrite[i] = true;
    }
  }

  /** return false if all fields of buffor are not avaiable to write */
  /** It checks is at least one true in canWirte */
  private boolean canWrite(){

      for (int i = 0; i< size; i++) {
        if(canWrite[i]){
          return true;
        }
      }
      return false;
    }

    private int findFieldToWrite(){
      for(int i=0;i<size;i++){
          if(canWrite[i]){
            return i;
          }
      }
      throw new IllegalStateException("at least one files should be free");

    }

    private int findFieldToRead(){
      for(int i=0;i<size;i++){
        if(!canWrite[i]){
          return i;
        }
      }
      throw new IllegalStateException("at least one files should be free");
    }

  /** return false if all fields of buffor are not avaiable to write */
  /** It checks is at least one false in canWirte */
  private boolean canRead(){
    for (int i = 0; i< size; i++) {
      if(!canWrite[i]){
        return true;
      }
    }
    return false;
  }

  public synchronized void put(int value) {

    while (!canWrite()) {

      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    int index = findFieldToWrite();
    buffer[index] = value;
    canWrite[index] = false;
    notifyAll();
    System.out.print("Produkuje: [" );
    for (int v: buffer
         ) {
      System.out.print(" " + v + ",");
    }
    System.out.println("]");
  }


  public synchronized int get() {
    while (!canRead()) {

      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    int index = findFieldToRead();
    canWrite[index] = true;
    notifyAll();
    System.out.println("Konsumuje: " + buffer[index]);
    return buffer[index];
  }
}
