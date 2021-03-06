package finegrainedblocking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NodeListWithOneLock implements NodeList {

    private final Node nodeHead;
    private final Lock lock = new ReentrantLock();
    private final int cost;

    public NodeListWithOneLock(int cost) {
        this.nodeHead = new Node(null,null,cost);
        this.cost = cost;
    }

    @Override
    public boolean contains(Object o){

        lock.lock();

        try {
            Node pointer = nodeHead;
            Node nextPointer = pointer.getNext();

            while (pointer.hasNext()) {
                if (nextPointer.getNode().equals(o)) {
                    return true;
                }
                pointer = pointer.getNext();
                nextPointer = nextPointer.getNext();
            }
        }
        finally {
            lock.unlock();

        }
        return false;
    }

    @Override
    public boolean remove(Object o){

        lock.lock();
        try {
            Node pointer = nodeHead;
            Node nextPointer = pointer.getNext();

            while (pointer.hasNext()) {
                if (nextPointer.getNode().equals(o)) {
                    pointer.setNext(nextPointer.getNext());
                    return true;
                }
                pointer = pointer.getNext();
                nextPointer = nextPointer.getNext();
            }
        }
        finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean add(Object o){

        lock.lock();
        try {
            Node pointer = nodeHead;
            Node nextPointer = pointer.getNext();

            while (pointer.hasNext()) {
                pointer = pointer.getNext();
                nextPointer = nextPointer.getNext();
            }

            pointer.setNext(new Node(o, null, cost));
        }
        finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public String toString() {

        if(nodeHead.hasNext()){
            return "[" + nodeHead.getNext().toString() + "]";
        }
        return "[]";
    }
}
