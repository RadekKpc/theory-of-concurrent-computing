package readersandwriters;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {

    Object node;
    Node next;
    final Lock lock = new ReentrantLock();

    public Node(Object node, Node next) {
        this.node = node;
        this.next = next;
    }

    public boolean hasNext(){
        return next != null;
    }
    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

    public Object getNode() {
        return node;
    }

    public void setNode(Object node) {
        this.node = node;
    }


    public Lock getLock() {
        return lock;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
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
