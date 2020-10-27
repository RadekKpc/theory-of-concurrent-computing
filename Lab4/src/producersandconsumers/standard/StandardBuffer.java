package producersandconsumers.standard;

import producersandconsumers.IBuffer;


public class StandardBuffer implements IBuffer {

    private final int[] buffer;
    private final boolean[] canWrite;
    private final int size;
    private final CountingSemaphore readSemaphore;
    private final CountingSemaphore writeSemaphore;
    private final CountingSemaphore indexSemaphore;

    public StandardBuffer(int size){
        readSemaphore = new CountingSemaphore(0);
        writeSemaphore = new CountingSemaphore(size);
        indexSemaphore = new CountingSemaphore(1);

        this.size = size;
        buffer = new int[size];
        canWrite = new boolean[size];

        for (int i =0;i < size; i++){
            buffer[i]= 0;
            canWrite[i] = true;
        }
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
