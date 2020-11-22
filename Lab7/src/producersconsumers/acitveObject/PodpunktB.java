package producersconsumers.acitveObject;

public class PodpunktB {
    public static void main(String[] args) {
        BufferProxy buffer = new BufferProxy(10);
        int n1 = 5;
        int n2 = 5;

        for(int i=0;i<n1 ;i++){
            Producer producer = new Producer(buffer);
            producer.start();
        }
        for(int i=0;i<n2 ;i++){
            Consumer consumer = new Consumer(buffer);
            consumer.start();
        }

    }
}
