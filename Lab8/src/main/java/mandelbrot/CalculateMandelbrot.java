package mandelbrot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculateMandelbrot {

    private final int poolSize;
    private final int iterations;
    private final int width;
    private final int height;

    public CalculateMandelbrot(int poolSize, int maxIterations, int width, int height) {
        this.poolSize = poolSize;
        this.iterations = maxIterations;
        this.width = width;
        this.height = height;
    }

    public long calculate(){
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        List<Future<Three<Integer>>> result;
        List<UnitWork> tasks = new ArrayList<>();

        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                tasks.add(new UnitWork(iterations,i,j));
            }
        }

        long startTime = System.nanoTime();

        try {
            result = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.nanoTime() - startTime;
    }
}
