package readersandwriters;

import java.util.concurrent.Semaphore;

public class Buffer implements IBuffer{
    private final int[] buffer;
    private final int size;
    private final int[] howManyReadersInField;
    private final Semaphore[] readSemaphore;
    private final Semaphore[] indexSemaphore;
    private final long readTime;
    private final long writeTime;

    public Buffer(int size,long readTime, long writeTime){
        this.size = size;
        this.readTime = readTime;
        this.writeTime = writeTime;

        buffer = new int[size];
        howManyReadersInField = new int[size];
        readSemaphore = new Semaphore[size];
        indexSemaphore = new Semaphore[size];

        for (int i =0;i < size; i++){
            buffer[i]= 0;
            indexSemaphore[i] = new Semaphore(1);
            readSemaphore[i] = new Semaphore(1);
            howManyReadersInField[i] = 0;
        }

    }

    public void put(int value,int index) {

        try {
            readSemaphore[index].acquire();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buffer[index] = value;
        try {
            Thread.sleep(0,(int) readTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readSemaphore[index].release();

    }

    public int get(int index){

        try {
            indexSemaphore[index].acquire();
            howManyReadersInField[index]++;

            //when we are first, we should to block writer
            if(howManyReadersInField[index] == 1){
                readSemaphore[index].acquire();
            }
            indexSemaphore[index].release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        int result = buffer[index];
        try {
            Thread.sleep(0,(int) readTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            indexSemaphore[index].acquire();

            howManyReadersInField[index]--;
            //when we are last reader, we should unlock writer
            if(howManyReadersInField[index] == 0){
                readSemaphore[index].release();
            }
            indexSemaphore[index].release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public int getSize() {
        return size;
    }
}
