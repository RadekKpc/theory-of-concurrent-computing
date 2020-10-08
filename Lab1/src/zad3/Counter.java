package zad3;

public class Counter {

    int counter;

    public Counter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void inc(){
        counter +=1;
    }

    public void dec(){
        counter -=1;
    }
}
