package Arguments;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Arguments {

    public SortingOrder order;

    public DataType dataType;

    public ConcurrentLinkedQueue<String> inQueue;

    public String out;

    public Arguments(SortingOrder order, DataType dataType, String out, ConcurrentLinkedQueue<String> inQueue) {
        this.order = order;
        this.dataType = dataType;
        this.inQueue = inQueue;
        this.out = out;
    }

}