package Arguments;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Arguments {

    public String order;

    public String dataType;

    public ConcurrentLinkedQueue<String> inQueue;

    public String out;

    public Arguments(String order, String dataType, String out, ConcurrentLinkedQueue<String> inQueue) {
        this.order = order;
        this.dataType = dataType;
        this.inQueue = inQueue;
        this.out = out;
    }

}