package philosophers;

//philosophers.symetric.Fork;
//philosophers.twoForksSimultaneously.Fork;
//philosophers.waiter.Fork;

import org.math.plot.Plot2DPanel;
import philosophers.symmetric.SymmetricPhilosophers;
import philosophers.twoForksSimultaneously.TwoForksPhilosophers;
import philosophers.waiter.WaiterPhilosophers;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class Measurements {

    public static void main(String[] args) {

        System.out.println("Measure Dinner change");
        List<List<Double>> dinnerChange = measureForParameters(1,10,10,40,40,0,1);
        double[] symmetricDinner =   dinnerChange.get(0).stream().mapToDouble(Double::doubleValue).toArray();
        double[] twoForksDinner =   dinnerChange.get(1).stream().mapToDouble(Double::doubleValue).toArray();
        double[] waiterDinner =   dinnerChange.get(2).stream().mapToDouble(Double::doubleValue).toArray();
        double[] xDinner =   dinnerChange.get(3).stream().mapToDouble(Double::doubleValue).toArray();

        System.out.println("ASdas");
        Arrays.stream(symmetricDinner).forEach(System.out::println);


        draw2DPlots(xDinner,symmetricDinner,xDinner,twoForksDinner,xDinner,waiterDinner,"Symmetric","Two Forks","Waiter","Time in function of dinner amount");

        System.out.println("Measure ThinkTime change");
        List<List<Double>> thinkTimeChange = measureForParameters(10,1,10,40,40,1,1);
        double[] symmetricThinkTime =   thinkTimeChange.get(0).stream().mapToDouble(Double::doubleValue).toArray();
        double[] twoForksThinkTime =   thinkTimeChange.get(1).stream().mapToDouble(Double::doubleValue).toArray();
        double[] waiterThinkTime =   thinkTimeChange.get(2).stream().mapToDouble(Double::doubleValue).toArray();
        double[] xThinkTime =   thinkTimeChange.get(3).stream().mapToDouble(Double::doubleValue).toArray();

        draw2DPlots(xDinner,symmetricThinkTime,xThinkTime,twoForksThinkTime,xThinkTime,waiterThinkTime,"Symmetric","Two Forks","Waiter","Time in function of think time");

        System.out.println("Measure EatTime change");
        List<List<Double>> eatTimeChange = measureForParameters(10,10,1,40,40,2,1);
        double[] symmetricEatTimeChange =   eatTimeChange.get(0).stream().mapToDouble(Double::doubleValue).toArray();
        double[] twoForksEatTimeChange =   eatTimeChange.get(1).stream().mapToDouble(Double::doubleValue).toArray();
        double[] waiterEatTimeChange =   eatTimeChange.get(2).stream().mapToDouble(Double::doubleValue).toArray();
        double[] xEatTimeChange =   eatTimeChange.get(3).stream().mapToDouble(Double::doubleValue).toArray();

        draw2DPlots(xEatTimeChange,symmetricEatTimeChange,xEatTimeChange,twoForksEatTimeChange,xEatTimeChange,waiterEatTimeChange,"Symmetric","Two Forks","Waiter","Time in function of eat time");
    }


    private static List<List<Double>> measureForParameters(int dinnerSize, int thinkTimeMs, int eatTimeMs, int pollsSize,int measurementsSize,int changingValue,int step){
        ExecutorService poolSymmetric = Executors.newFixedThreadPool(pollsSize);
        ExecutorService poolTwoForks = Executors.newFixedThreadPool(pollsSize);
        ExecutorService poolWaiter = Executors.newFixedThreadPool(pollsSize);

        List<Future<Long>> futuresTwoForks = new ArrayList<>();
        List<Future<Long>> futuresSymmetric = new ArrayList<>();
        List<Future<Long>> futuresWaiter = new ArrayList<>();

        List<Double> symmetricResult = new ArrayList<>();
        List<Double> twoForksResult = new ArrayList<>();
        List<Double> waiterResult = new ArrayList<>();
        List<Double> x = new ArrayList<>();

        for(int i=0;i<measurementsSize;i++){

            switch (changingValue) {
                case 0 -> dinnerSize += step;
                case 1 -> thinkTimeMs += step;
                case 2 -> eatTimeMs += step;
            }

            SymmetricPhilosophers symmetricPhilosophers = new SymmetricPhilosophers(dinnerSize,thinkTimeMs,eatTimeMs);
            TwoForksPhilosophers twoForksPhilosophers = new TwoForksPhilosophers(dinnerSize,thinkTimeMs,eatTimeMs);
            WaiterPhilosophers waiterPhilosophers = new WaiterPhilosophers(dinnerSize,thinkTimeMs,eatTimeMs);

            futuresSymmetric.add(poolSymmetric.submit(symmetricPhilosophers));
            futuresTwoForks.add(poolTwoForks.submit(twoForksPhilosophers));
            futuresWaiter.add(poolWaiter.submit(waiterPhilosophers));

            switch (changingValue) {
                case 0 -> x.add((double) dinnerSize);
                case 1 -> x.add((double) thinkTimeMs);
                case 2 -> x.add((double) eatTimeMs);
            }

        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            for(int i=0;i< measurementsSize;i++){
                try {symmetricResult.add((double) futuresSymmetric.get(i).get(1,TimeUnit.SECONDS));
                }
                catch (TimeoutException e){
                    symmetricResult.add(null);
                    System.out.println("InfiniteLoop 1");
                }
                twoForksResult.add((double) futuresTwoForks.get(i).get());

                waiterResult.add((double) futuresWaiter.get(i).get());

                System.out.println("Progress: " + (double) (i + 1)/measurementsSize);
            }
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        double max = Math.max(
                Math.max(symmetricResult.stream().filter(Objects::nonNull).max(Double::compareTo).orElse((double) 0),
                        twoForksResult.stream().filter(Objects::nonNull).max(Double::compareTo).orElse((double) 0)),
                waiterResult.stream().filter(Objects::nonNull).max(Double::compareTo).orElse((double) 0));

//        to visualize infinite
        List finalResultOfSymmetric = Arrays.asList(symmetricResult.stream().map(a -> Objects.requireNonNullElseGet(a, () -> max * 1.5)).toArray());

        return List.of(finalResultOfSymmetric, twoForksResult, waiterResult, x);
    }

    private static void draw2DPlots(double[] arguments, double[] values, double[] arguments2, double[] values2, double[] arguments3, double[] values3, String name, String name2,String name3, String plotName){

        Plot2DPanel plot = new Plot2DPanel();
        // add a line plot to the PlotPanel
        // blue

        double max = Math.max(
                Math.max(Arrays.stream(arguments).max().orElse(0),
                        Arrays.stream(arguments).max().orElse(0)),
                Arrays.stream(arguments).max().orElse(0));
        double min = Math.min(
                Math.min(Arrays.stream(arguments).min().orElse(0),
                        Arrays.stream(arguments).min().orElse(0)),
                Arrays.stream(arguments).min().orElse(0));

        plot.setFixedBounds(1,min,max);

        plot.addLinePlot(name, arguments, values);
        //red
        plot.addLinePlot(name2, arguments2, values2);
        // green
        plot.addLinePlot(name3, arguments3, values3);

        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame(plotName);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
