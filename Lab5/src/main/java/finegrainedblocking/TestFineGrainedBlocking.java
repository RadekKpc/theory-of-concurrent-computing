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

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.max;

public class TestFineGrainedBlocking {

    public static void main(String[] args) {

//        za zasadne uznam zbadanie wydajności list w zależności nie tylko od kosztu ale również od ilośći wątków obsługujących ta listę, gdyż dla jedengo wątku nie ma to sensu,
//        zajmuje on po porstu cała kolejke, przewaga kolejni z wieloma blokadami polega włąsśnie na wielodostepie zatem zbadam tez w zaleznosci od roznej liczby wątków.

        final int operations = 1;
        final int minCost = 1;
        final int maxCost = 100;
        final int costStep = 1;
        final int minExecutors = 10;
        final int maxExecutors = 10;

        final int countOfCost = maxCost - minCost + 1;
        final int countOfExecutors = maxExecutors - minExecutors + 1;

        long[][] oneLock = new long[countOfCost][countOfExecutors];
        long[][] multipleLocks = new long[countOfCost][countOfExecutors];

        for(int cost=minCost;cost<=maxCost;cost+=costStep){
            for(int ex =minExecutors; ex <= maxExecutors; ex++) {

                ArrayList<Thread> executorsOneLock = new ArrayList<>();
                ArrayList<Thread> executorsMultipleLocks = new ArrayList<>();

                NodeList oneLockList = new NodeListWithOneLock(cost);
                NodeList multipleLocksList = new NodeListMultipleLocks(cost);

                for (int i = 0; i < ex; i++) {
                    executorsOneLock.add(new NodeListExecutor(oneLockList, operations));
                    executorsMultipleLocks.add(new NodeListExecutor(multipleLocksList, operations));
                }

                oneLock[cost - minCost][ex - minExecutors] = measureTime(executorsOneLock);
                multipleLocks[cost - minCost][ex - minExecutors] = measureTime(executorsMultipleLocks);
            }

        }

        printResult(oneLock,countOfCost,countOfExecutors);
        printResult(multipleLocks,countOfCost,countOfExecutors);

        calculateAverage(oneLock,countOfCost,countOfExecutors,"One Lock");
        calculateAverage(multipleLocks,countOfCost,countOfExecutors,"Multiple Lock");

        create3dPlot(oneLock,countOfCost,countOfExecutors,"One Lock \n t = time = f(koszt,liczba_watków)");
        create3dPlot(multipleLocks,countOfCost,countOfExecutors,"Multiple Lock \n t = time = f(koszt,liczba_watków)");

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
}

