package pipelining;

import java.util.concurrent.Semaphore;

class Buffer {

  public final int[] buffer;
  private final int[] whoCanModify;
  private final Semaphore[] countOfOperations;
  private final int size;
//  Semaphore for whoCanModify table
  private final Semaphore indexSemaphore;

  public Buffer(int size, int processesCount, int firstProcessNumber){
    this.size = size;
    buffer = new int[size];
    whoCanModify = new int[size];
    countOfOperations = new Semaphore[size];
    for(int i=0;i< processesCount;i++){
      if(i == firstProcessNumber){
        countOfOperations[i] = new Semaphore(size-1);
      }
      else {
        countOfOperations[i] = new Semaphore(0);
      }
    }
    for (int i =0;i < size; i++){
      buffer[i]= 0;
      whoCanModify[i] = 0;
    }

    indexSemaphore = new Semaphore(1);
  }

    private int findFieldForSpecificNumber(int processNumber){
      try {
        indexSemaphore.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      for(int i=0;i<size;i++){
          if(whoCanModify[i] == processNumber){
            indexSemaphore.release();
            return i;
          }
      }
      indexSemaphore.release();
      throw new IllegalStateException("at least one field should be free to modify");

    }

    private void giveToTheNextProcess(int index,int whoIsNext){
      try {
        indexSemaphore.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      whoCanModify[index] = whoIsNext;
      countOfOperations[whoIsNext].release();
      indexSemaphore.release();
    }

    public void put(int value,int processNumber, int nextProcess) {
      try {
        countOfOperations[processNumber].acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      int index = findFieldForSpecificNumber(processNumber);
      buffer[index] = value;
      giveToTheNextProcess(index,nextProcess);
    }

  public void calculate(int processNumber, int nextProcess){
    try {
      countOfOperations[processNumber].acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int index = findFieldForSpecificNumber(processNumber);
    buffer[index] = buffer[index] + 1;
    giveToTheNextProcess(index,nextProcess);
  }

  public int get(int processNumber,int nextProcess){

    try {
      countOfOperations[processNumber].acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int index = findFieldForSpecificNumber(processNumber);
    int result = buffer[index];
    giveToTheNextProcess(index,nextProcess);

    return result;

  }
}
