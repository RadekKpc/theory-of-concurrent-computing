package mandelbrot;

import java.util.concurrent.Callable;

public class UnitWork implements Callable<Three<Integer>> {
    private final int iterations;
    private final int x;
    private final int y;

    public UnitWork(int iterations, int x, int y) {
        this.iterations = iterations;
        this.x = x;
        this.y = y;
    }

    @Override
    public Three<Integer> call() throws Exception {
        double zx = 0;
        double zy = 0;
        double tmp = 0;
        double ZOOM = 150;
        double cX = (x - 400) / ZOOM;
        double cY = (y - 300) / ZOOM;
        int iter = iterations;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return new Three<Integer>(x,y,iter | (iter << 8));
    }


}
