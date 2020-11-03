package readersandwriters;

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

public class TestReadersAndWriters {

    public static void main(String[] args) {

        NodeList nodeList = new NodeListWithOneLock();
        final int minReaders =10;
        final int maxReaders = 100;
        final int minWriters = 1;
        final int maxWriters = 10;

        for(int r = minReaders; r<= maxReaders; r++){
            for(int w = minWriters; w <= maxWriters; w++){

            }
        }
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
        chart.open(surfaceName, 600, 600);
    }

    private static void calculateAverage(long[][] tab, int M, String info){
        long sum = Arrays.stream(tab).flatMapToLong(Arrays::stream).reduce(0, Long::sum);
        System.out.println("Average for" + info + ": " + (double) sum/(1000000*M) + "ms");
    }
}

