package zad3;

public class DecThread extends Thread {

    private ThreadManager threadManager;

    DecThread(ThreadManager threadManager){
        super();
        this.threadManager = threadManager;
    }

    @Override
    public void run(){
        for(int i=0;i<1000000;){

            threadManager.getCounter().dec();
                i++;

        }
        threadManager.setFirstFisihedWork(true);
    }

}
