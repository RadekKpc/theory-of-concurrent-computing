package finegrainedblocking;

public class NodeListMultipleLocks implements NodeList {

    private final Node nodeHead;
    private final int cost;

    public NodeListMultipleLocks(int cost) {
        this.nodeHead = new Node(null,null,cost);
        this.cost = cost;
    }

    @Override
    public boolean contains(Object o){
        Node pointer = nodeHead;

        pointer.lock();
        Node nextPointer = pointer.getNext();

        while (pointer.hasNext()){
            nextPointer.lock();
            if(nextPointer.getNode().equals(o)){
                pointer.unlock();
                nextPointer.unlock();
                return true;
            }
            Node tmp = pointer;
            pointer = pointer.getNext();
            tmp.unlock();
            nextPointer = nextPointer.getNext();
        }
        pointer.unlock();
        return false;
    }

    @Override
    public boolean remove(Object o){
        Node pointer = nodeHead;

        pointer.lock();
        Node nextPointer = pointer.getNext();

        while (pointer.hasNext()){
            nextPointer.lock();
            if(nextPointer.getNode().equals(o)){
                pointer.setNext(nextPointer.getNext());
                pointer.unlock();
                nextPointer.unlock();
                return true;
            }
            Node tmp = pointer;
            pointer = pointer.getNext();
            tmp.unlock();
            nextPointer = nextPointer.getNext();
        }
        pointer.unlock();
        return false;
    }

    @Override
    public boolean add(Object o){

        Node pointer = nodeHead;

        pointer.lock();
        Node nextPointer = pointer.getNext();

        while (pointer.hasNext()){
            nextPointer.lock();
            Node tmp = pointer;
            pointer = pointer.getNext();
            tmp.unlock();
            nextPointer = nextPointer.getNext();
        }

        pointer.setNext(new Node(o,null,cost));
        pointer.unlock();
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
