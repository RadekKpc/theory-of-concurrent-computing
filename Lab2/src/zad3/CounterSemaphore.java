package zad3;

public class CounterSemaphore {

    private int resources;
    private Semaphore semaphore;
    private Semaphore semaphore2;

    CounterSemaphore(int resources){
        this.resources = resources;
        semaphore = new Semaphore(false);
        semaphore2 = new Semaphore(true);
    }

    public void P() {

        semaphore2.P();
        resources -= 1;

        if( resources < 0){

            semaphore2.V();
            semaphore.P();
        }
        else {
            semaphore2.V();
        }
    }

    public void V() {

        semaphore2.P();

        resources +=1;
        if(resources <= 0) {
            semaphore.V();
        }
        semaphore2.V();

    }
}
