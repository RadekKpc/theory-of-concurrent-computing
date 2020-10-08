package zad3;

public class IncThread extends Thread {

    private ThreadManager threadManager;

    IncThread(ThreadManager threadManager){
        super();
        this.threadManager = threadManager;
    }

    @Override
    public void run(){
        for(int i=0;i<1000000;){
            if(threadManager.isFirstFisihedWork()){
                i++;
                threadManager.getCounter().inc();
            }
            else{
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        threadManager.setSecondFisihedWork(true);
    }
}
