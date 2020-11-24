package mandelbrot;

public class Three<T1> {

    T1 x;
    T1 y;
    T1 z;

    public Three(T1 x, T1 y, T1 z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public T1 getX() {
        return x;
    }

    public void setX(T1 x) {
        this.x = x;
    }

    public T1 getY() {
        return y;
    }

    public void setY(T1 y) {
        this.y = y;
    }

    public T1 getZ() {
        return z;
    }

    public void setZ(T1 z) {
        this.z = z;
    }
}
