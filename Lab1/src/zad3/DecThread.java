package zad3;

public class DecThread extends Thread {

    private ThreadManager threadManager;

    DecThread(ThreadManager threadManager){
        super();
        this.threadManager = threadManager;
    }

//    @Override
//    public void run(){
//        for(int i=0;i<10;){
//            threadManager.setFirst_ready(true);
//
//            if(threadManager.isFirstIn()){
//                i++;
//                threadManager.getCounter().dec();
//                threadManager.setFirst_ready(false);
//                threadManager.setFirstIn(false);
//                System.out.println("First work done");
//            }
//        }
//        threadManager.setFirstFisihedWork(true);
//    }

    @Override
    public void run(){
        for(int i=0;i<1000000;){

            threadManager.getCounter().dec();
                i++;

        }
        threadManager.setFirstFisihedWork(true);
    }

}
