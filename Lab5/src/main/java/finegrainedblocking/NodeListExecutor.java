package finegrainedblocking;

public class NodeListExecutor extends Thread {

    private final NodeList list;
    private final int operations;

    public NodeListExecutor(NodeList list,int operations){
        super();
        this.list = list;
        this.operations = operations;
    }

    @Override
    public void run() {

        for(int i=0;i< operations;i++){
            int ran = (int) (Math.random() * 20);
            list.contains(i);
            list.add(i);
            list.remove(ran);
        }
    }
}
