package finegrainedblocking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {

    Object node;
    Node next;
    final Lock lock = new ReentrantLock();
    final int cost;

    public Node(Object node, Node next, int cost) {
        this.node = node;
        this.next = next;
        this.cost = cost;
        executeCost();
    }

    public void executeCost(){
        try {
            Thread.sleep(0,cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNext(){
        executeCost();
        return next != null;
    }
    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

    public Object getNode() {
        executeCost();
        return node;

    }

    public void setNode(Object node) {
        this.node = node;
        executeCost();
    }


    public Lock getLock() {
        return lock;
    }

    public Node getNext() {
        executeCost();
        return next;
    }

    public void setNext(Node next) {
        executeCost();
        this.next = next;
    }

    @Override
    public String toString() {
        if(hasNext()){
            return node.toString() + " " + next.toString() + " ";
        }
        return node.toString();
    }
}
