package readersandwriters;

public class NodeListMultipleLocks implements NodeList {

    private final Node nodeHead;

    public NodeListMultipleLocks() {
        this.nodeHead = new Node(null,null);
    }

    @Override
    public boolean contains(Object o){
        Node pointer = nodeHead;
        Node nextPointer = pointer.getNext();

        pointer.lock();

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
        Node nextPointer = pointer.getNext();

        pointer.lock();

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
        Node nextPointer = pointer.getNext();

        pointer.lock();

        while (pointer.hasNext()){
            nextPointer.lock();
            Node tmp = pointer;
            pointer = pointer.getNext();
            tmp.unlock();
            nextPointer = nextPointer.getNext();
        }

        pointer.setNext(new Node(o,null));
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
