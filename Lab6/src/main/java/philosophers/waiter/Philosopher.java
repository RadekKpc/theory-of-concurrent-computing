package philosophers.waiter;

public class Philosopher extends Thread{
    public final String ANSI_RESET = "\u001B[0m";
    public final String COLOR;

    private final Fork leftFork;
    private final Fork rightFork;
    private final int thinkTimeMs;
    private final int eatTimeMs;
    private final String name;
    private final int dinnerSize;
    private int foodAtPlate;
    private Waiter waiter;

    public Philosopher(Fork leftFork, Fork rightFork, String name, int foodAtPlate, int thinkTimeMs, int eatTimeMs, String color,Waiter waiter) {
        super();
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.name = name;
        this.foodAtPlate = foodAtPlate;
        this.dinnerSize = foodAtPlate;
        this.thinkTimeMs = thinkTimeMs;
        this.eatTimeMs = eatTimeMs;
        this.COLOR = color;
        this.waiter = waiter;
    }

    private void think(){
        try {
            sleep(0,thinkTimeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat(){

        try {
            Thread.sleep(0,eatTimeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println(COLOR + name + " ate " + (dinnerSize - foodAtPlate) + " time " + ANSI_RESET);

        foodAtPlate --;

    }

    private boolean isDinnerFinished() {
        return  foodAtPlate == 0;
    }

    private void sayGoodbye(){
        System.out.println(COLOR + "My name is " + name + ". I finished my dinner :)" + ANSI_RESET);
    }

    @Override
    public void run(){

        while (!isDinnerFinished()){

            think();

            waiter.askAboutPlate();
            leftFork.take();
            rightFork.take();

            eat();

            rightFork.put();
            leftFork.put();
            waiter.realisePlate();

        }

//        sayGoodbye();

    }



}
