package producersconsumers.acitveObject;

import java.util.Arrays;

public class Buffer {

    private final int[] buffer;
    private final int size;
    private final boolean[] canWrite;

    public Buffer(int size){
        this.size = size;
        buffer = new int[size];
        canWrite =  new boolean[size];
        for (int i =0;i < size; i++){
            buffer[i]= 0;
            canWrite[i] = true;
        }
    }

    public boolean canWrite(){

        for (int i = 0; i< size; i++) {
            if(canWrite[i]){
                return true;
            }
        }
        return false;
    }

    public boolean canRead(){
        for (int i = 0; i< size; i++) {
            if(!canWrite[i]){
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

    public void put(int value) {

        int index = findFieldToWrite();
        buffer[index] = value;
        canWrite[index] = false;

    }


    public int get() {
        int index = findFieldToRead();
        canWrite[index] = true;
        return buffer[index];
    }

    @Override
    public String toString() {
        return "Buffer{" +
                "buffer=" + Arrays.toString(buffer) +
                '}';
    }
}
