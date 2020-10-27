package producersandconsumers;

public interface IBuffer {

    void put(int value);
    int get();

}
