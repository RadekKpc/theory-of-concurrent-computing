package finegrainedblocking;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Math.max;

public class TestFineGrainedBlocking {

    public static void main(String[] args) {

//        za zasadne uznam zbadanie wydajności list w zależności nie tylko od kosztu ale również od ilośći wątków obsługujących ta listę, gdyż dla jedengo wątku nie ma to sensu,
//        zajmuje on po porstu cała kolejke, przewaga kolejki z wieloma blokadami polega właśnie na wielodostepie zatem zbadam tez zaleznosc czasu od roznej liczby wątków.

//        Global Parameters
        final int operations = 1;

//       Cost measurement parameters
        final int minCost = 1;
        final int maxCost = 100;
        final int costStep = 1;
        final int threadsForCostMeasurement = 5;
        final int countOfCost = maxCost - minCost + 1;

//       Threads measurement parameters

        final int minExecutors = 1;
        final int maxExecutors = 30;
        final int costForThreadMeasurement = 10;
        final int countOfExecutors = maxExecutors - minExecutors + 1;

//        Visualization

        double[] oneLockCost = new double[countOfCost];
        double[] multipleLocksCost = new double[countOfCost];
        double[] xCostAxis = new double[countOfCost];

        double[] oneLockThreads = new double[countOfExecutors];
        double[] multipleLocksThreads = new double[countOfExecutors];
        double[] xThreadsAxis = new double[countOfExecutors];

        double[] oneLockCostOneThread = new double[countOfCost];
        double[] multipleLocksCostOneThread = new double[countOfCost];
        double[] xConstOneThreadAxis = new double[countOfCost];

//        Measurement the time for multiple Threads and const cost

        for(int cost=minCost;cost<=maxCost;cost+=costStep) {

            NodeList oneLockList = new NodeListWithOneLock(cost);
            NodeList multipleLocksList = new NodeListMultipleLocks(cost);

            ArrayList<Thread> executorsOneLock = new ArrayList<>();
            ArrayList<Thread> executorsMultipleLocks = new ArrayList<>();

            for (int i = 0; i < threadsForCostMeasurement; i++) {
                executorsOneLock.add(new NodeListExecutor(oneLockList, operations));
                executorsMultipleLocks.add(new NodeListExecutor(multipleLocksList, operations));
            }

            oneLockCost[cost - minCost] = (double) measureTime(executorsOneLock);
            multipleLocksCost[cost - minCost] = (double) measureTime(executorsMultipleLocks);
            xCostAxis[cost - minCost] = cost;
            System.out.println("Cost Measurement: " +  ((double) cost/maxCost) * 100.0 + "%");
        }

//        Measurement the time for multiple costs and const count of threads
        System.out.println("Threads measurement:");
        for(int ex =minExecutors; ex <= maxExecutors; ex++) {

            NodeList oneLockList = new NodeListWithOneLock(costForThreadMeasurement);
            NodeList multipleLocksList = new NodeListMultipleLocks(costForThreadMeasurement);

            ArrayList<Thread> executorsOneLock = new ArrayList<>();
            ArrayList<Thread> executorsMultipleLocks = new ArrayList<>();

            for (int i = 0; i < ex; i++) {
                executorsOneLock.add(new NodeListExecutor(oneLockList, operations));
                executorsMultipleLocks.add(new NodeListExecutor(multipleLocksList, operations));
            }

            oneLockThreads[ex - minExecutors] = (double) measureTime(executorsOneLock);
            multipleLocksThreads[ex - minExecutors] = (double) measureTime(executorsMultipleLocks);
            xThreadsAxis[ex - minExecutors] = ex;

            System.out.println("Threads Measurement: " + ((double) ex/maxExecutors) * 100 + "%");

        }

//        Measurement the time for multiple costs and one thread

        for(int cost=minCost;cost<=maxCost;cost+=costStep) {

            NodeList oneLockList = new NodeListWithOneLock(cost);
            NodeList multipleLocksList = new NodeListMultipleLocks(cost);

            ArrayList<Thread> executorsOneLock = new ArrayList<>();
            ArrayList<Thread> executorsMultipleLocks = new ArrayList<>();

            executorsOneLock.add(new NodeListExecutor(oneLockList, operations));
            executorsMultipleLocks.add(new NodeListExecutor(multipleLocksList, operations));

            oneLockCostOneThread[cost - minCost] = (double) measureTime(executorsOneLock);
            multipleLocksCostOneThread[cost - minCost] = (double) measureTime(executorsMultipleLocks);
            xConstOneThreadAxis[cost - minCost] = cost;
            System.out.println("Cost Measurement one thread: " + ((double) cost/maxCost) * 100 + "%");
        }
//        visualization

        draw2DPlots(xCostAxis,oneLockCost,xCostAxis,multipleLocksCost,"Lock na całą listę","Lock na każdy element listy","Porównanie zalezności czasu od kosztu");
        draw2DPlots(xThreadsAxis,oneLockThreads,xThreadsAxis,multipleLocksThreads,"Lock na całą listę","Lock na każdy element listy","Porównanie zalezności czasu od ilosci wątków");
        draw2DPlots(xConstOneThreadAxis,oneLockCostOneThread,xConstOneThreadAxis,multipleLocksCostOneThread,"Lock na całą listę","Lock na każdy element listy","Porównanie zalezności czasu od ksoztu dla jedengo wątku");

    }
    private static void printResult(long[][] table, int X,int Y){
        for(int i=0;i<X;i++){
            System.out.println();
            for(int j =0;j<Y;j++){
                System.out.print(table[i][j]);
                System.out.print(" ");
            }
        }
    }


    private static long measureTime(ArrayList<Thread> threads){
        long startTime = System.nanoTime();

        for( Thread thread: threads){
            thread.start();
        }

        for( Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return System.nanoTime() - startTime;
    }

    private static void create3dPlot(long[][] table,int X,int Y,String surfaceName){

// Define a function to plot
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                if(x >= X || y  >= Y) return 0;
                return (double) table[(int) x ][ (int) y]/1000000;
            }
        };

// Define range and precision for the function to plot
        Range range = new Range(0, max(X,Y));
        int steps = 50;

// Create a surface drawing that function
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        surface.setFaceDisplayed(true);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);

// Create a chart and add the surface
        Chart chart = new AWTChart(Quality.Advanced);
        chart.add(surface);
        chart.open(surfaceName, 600, 600);
    }

    private static void calculateAverage(long[][] tab, int X,int Y, String info){
        long sum = Arrays.stream(tab).flatMapToLong(Arrays::stream).reduce(0, Long::sum);
        System.out.println("Average for" + info + ": " + (double) sum/(1000000*(X*Y)) + "ms");
    }

    private static void draw2DPlot(double[] arguments, double[] values){

        Plot2DPanel plot = new Plot2DPanel();
        // add a line plot to the PlotPanel
        plot.addLinePlot("my plot", arguments, values);

        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(plot);
        frame.setVisible(true);
    }

    private static void draw2DPlots(double[] arguments, double[] values, double[] arguments2, double[] values2, String name, String name2, String plotName){

        Plot2DPanel plot = new Plot2DPanel();
        // add a line plot to the PlotPanel
        plot.addLinePlot(name, arguments, values);
        plot.addLinePlot(name2, arguments2, values2);

        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame(plotName);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}

