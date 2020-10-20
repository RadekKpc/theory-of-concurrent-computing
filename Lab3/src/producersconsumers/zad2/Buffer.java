package producersconsumers.zad2;

class Buffer {

  private int[] buffer;
  private boolean[] canWrite;
  private final int size;
  private Semaphore readSemaphore;
  private Semaphore writeSemaphore;
  public Buffer(int size){
    this.size = size;
    buffer = new int[size];
    canWrite = new boolean[size];

    for (int i =0;i < size; i++){
      buffer[i]= 0;
      canWrite[i] = true;
    }
    writeSemaphore = new Semaphore(false);
    readSemaphore = new Semaphore(false);
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

  public void put(int value) {

    if (!canWrite()){
      writeSemaphore.P();
    }

    int index = findFieldToWrite();
    buffer[index] = value;
    canWrite[index] = false;
    readSemaphore.V();

  }


  public int get() {

    if(!canRead()){
      readSemaphore.P();
    }

    int index = findFieldToRead();
    canWrite[index] = true;
    int result = buffer[index];
    writeSemaphore.V();
    return result;
  }
}
