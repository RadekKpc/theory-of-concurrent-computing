package producersconsumers.zad2;

import java.util.concurrent.Semaphore;

class Buffer {

  private final int[] buffer;
  private final boolean[] canWrite;
  private final int size;
  private final Semaphore readSemaphore;
  private final Semaphore writeSemaphore;
  private final Semaphore indexSemaphore;

  public Buffer(int size){
    this.size = size;
    buffer = new int[size];
    canWrite = new boolean[size];

    for (int i =0;i < size; i++){
      buffer[i]= 0;
      canWrite[i] = true;
    }
    writeSemaphore = new Semaphore(size-1);
    indexSemaphore = new Semaphore(1);
    readSemaphore = new Semaphore(0);
  }

    private int findFieldToWrite(){
      for(int i=0;i<size;i++){
          if(canWrite[i]){
            canWrite[i] = false;
            return i;
          }
      }
      throw new IllegalStateException("at least one field should be free to write");

    }

    private int findFieldToRead(){
      for(int i=0;i<size;i++){
        if(!canWrite[i]){
          canWrite[i] = true;
          return i;
        }
      }
      throw new IllegalStateException("at least one filed should be free to read");
    }



  public void put(int value) {

    try {
      writeSemaphore.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      indexSemaphore.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


      int index = findFieldToWrite();

      indexSemaphore.release();

      buffer[index] = value;


    readSemaphore.release();

  }


  public int get(){

    try {
      readSemaphore.acquire();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }


    try {
      indexSemaphore.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

      int index = findFieldToRead();


      indexSemaphore.release();
      int result = buffer[index];
      writeSemaphore.release();

      return result;

  }
}
