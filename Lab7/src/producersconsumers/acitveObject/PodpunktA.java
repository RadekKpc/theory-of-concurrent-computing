package producersconsumers.acitveObject;

public class PodpunktA {
    public static void main(String[] args) {
        BufferProxy buffer = new BufferProxy(10);

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
