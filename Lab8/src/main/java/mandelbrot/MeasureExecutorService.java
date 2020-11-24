package mandelbrot;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.util.Arrays;

public class MeasureExecutorService {

    public static void main(String[] args) {
        int width = 800;
        int height = 600;

        int iterStep = 100;
        int iterStartIteration = 500;
        int iterSteps = 100;
        int iterThreadPool = 10;
        double[] iterResult = new double[iterSteps];
        double[] iterX = new double[iterSteps];

        int threadPoolStep = 1;
        int threadPoolStart = 1;
        int threadPoolSteps = 100;
        int threadPoolMaxIter = 10000;
        double[] threadResult = new double[100];
        double[] threadX = new double[iterSteps];

        for(int i=0;i<iterSteps;i++){
            iterX[i] = iterStartIteration;
            CalculateMandelbrot calculateMandelbrot = new CalculateMandelbrot(iterThreadPool,iterStartIteration,width,height);
            iterResult[i] = (double) (calculateMandelbrot.calculate()/1000000);
            iterStartIteration += iterStep;
            System.out.println("Iteration progress: " + (double) (i + 1)/iterSteps);
        }

        for(int i=0;i<threadPoolSteps;i++){
            threadX[i] = threadPoolStart;
            CalculateMandelbrot calculateMandelbrot = new CalculateMandelbrot(threadPoolStart,threadPoolMaxIter,width,height);
            threadResult[i] = (double) (calculateMandelbrot.calculate()/1000000);
            threadPoolStart += threadPoolStep;
            System.out.println("Thread progress: " + (double) (i + 1)/threadPoolSteps);
        }

        draw2DPlot(iterX,iterResult,"","The time in function of max iterations");
        draw2DPlot(threadX,threadResult,"","The time in function of the thread pool size ");
    }

    private static void draw2DPlot(double[] arguments, double[] values, String name, String plotName){

        Plot2DPanel plot = new Plot2DPanel();
        // add a line plot to the PlotPanel
        // blue

//        double max = Math.max(
//                Math.max(Arrays.stream(values).max().orElse(0),
//                        Arrays.stream(values2).max().orElse(0)),
//                Arrays.stream(values3).max().orElse(0));
//
//        double min = Math.min(
//                Math.min(Arrays.stream(values).min().orElse(0),
//                        Arrays.stream(values2).min().orElse(0)),
//                Arrays.stream(values3).min().orElse(0));

        //blue
        plot.addLinePlot(name, arguments, values);
        //red
//        plot.addLinePlot(name2, arguments2, values2);
//        // green
//        plot.addLinePlot(name3, arguments3, values3);

        // set y-axis boundary
//        plot.setFixedBounds(1,min,max);

        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame(plotName);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
