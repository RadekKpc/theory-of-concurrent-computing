package producersandconsumers;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import producersandconsumers.library.LibBuffer;
import producersandconsumers.standard.StandardBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        System.out.println("Compare java monitors vs concurrency utilities");
        final int M = 30;
        final int produceNumber = 10;
        long[][] libTimes = new long[M][M];
        long[][] standardTimes = new long[M][M];

        for(int producers = 1; producers <= M ; producers++){
            for(int consumers = 1; consumers <= M; consumers++){

                ArrayList<Thread> libThreads = new ArrayList<Thread>();
                ArrayList<Thread> standardThreads = new ArrayList<Thread>();

                LibBuffer libBuffer = new LibBuffer(M);
                StandardBuffer standardBuffer = new StandardBuffer(M);

//                rozdzielenie rpacy pomiędzy wątki, tyle ile producenci wyprodukują konsumęci musza przetworzyć
                int[] countOfConsumersOperations = new int[consumers];
                for(int i =0;i<consumers;i++){
                    countOfConsumersOperations[i] = 0;
                }
                int poolOfOperations = producers*produceNumber;

                while (poolOfOperations != 0){
                    countOfConsumersOperations[poolOfOperations%consumers]++;
                    poolOfOperations --;
                }

//                add threads into list
                for(int i=0;i<producers ;i++){
                    libThreads.add(new Producer(libBuffer, produceNumber));
                    standardThreads.add(new Producer(standardBuffer, produceNumber));
                }

                for(int i=0;i<consumers ;i++){
                    libThreads.add(new Consumer(libBuffer, countOfConsumersOperations[i]));
                    standardThreads.add(new Consumer(standardBuffer, countOfConsumersOperations[i]));
                }

//              calculate time

                libTimes[producers-1][consumers-1] = measureTime(libThreads);
                standardTimes[producers-1][consumers-1] = measureTime(standardThreads);
            }
        }

        printResult(standardTimes,M);
        System.out.println();
        printResult(libTimes,M);

        create3dPlot(libTimes,M,"Library Plot");
        create3dPlot(standardTimes,M,"Standard Java  Plot");

        long[][] diffTable = new long[M][M];
        for(int i= 0;i<M;i++){
            for(int j=0;j<M;j++){
                diffTable[i][j] =  standardTimes[i][j] - libTimes[i][j];
            }
        }
        create3dPlot(diffTable,M,"Difference");

        calculateAverage(standardTimes,M,"Standard Java");
        calculateAverage(libTimes,M,"Library");
    }

    private static void printResult(long[][] table, int M){
        for(int i=0;i<M;i++){
            System.out.println();
            for(int j =0;j<M;j++){
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

    private static void create3dPlot(long[][] table,int M,String surfaceName){

// Define a function to plot
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                return (double) table[(int) x][ (int) y]/1000000;
            }
        };

// Define range and precision for the function to plot
        Range range = new Range(0, M-1);
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
        chart.open(surfaceName, 1000, 1000);
    }

    private static void calculateAverage(long[][] tab, int M, String info){
        long sum = Arrays.stream(tab).flatMapToLong(Arrays::stream).reduce(0, Long::sum);
        System.out.println("Average for" + info + ": " + (double) sum/(1000000*M) + "ms");
    }

}
