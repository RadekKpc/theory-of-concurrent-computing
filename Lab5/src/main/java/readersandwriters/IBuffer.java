package readersandwriters;

public interface IBuffer {
    void put(int value,int index);
    int get(int index);
    int getSize();
}