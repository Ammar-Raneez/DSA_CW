import java.io.FileNotFoundException;

public class FordFulkerson {
    //*will hold the path, on how we reach each vertex*//
    private FlowEdge[] edgeTo;
    //*value of the flow*//
    private int flowValue;

    public FordFulkerson(FlowNetwork flowNetwork, int source, int target) throws InterruptedException {
        //*start by initializing flow to 0*//
        this.flowValue = 0;

        while (hasAugmentingPath(flowNetwork, source, target)) {
            double bottleneckCapacity = Integer.MAX_VALUE;

            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                bottleneckCapacity = Math.min(bottleneckCapacity, edgeTo[v].residualCapacity(v));
            }

            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                edgeTo[v].addResidualFlow(v, (int) bottleneckCapacity);
            }

            System.out.println("Bottleneck Capacity: " + bottleneckCapacity);
            System.out.print("Flow Value Incrementing from: " + flowValue + " to: ");
            flowValue += bottleneckCapacity;
            System.out.println(flowValue);
            System.out.println("Through path: \n");
            Thread.sleep(1000);
        }
    }

    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int source, int target) {
        edgeTo = new FlowEdge[flowNetwork.getNumberOfVertices()];
        return flowNetwork.breadthFirstSearch(source, edgeTo, target);
    }

    public int getFlowValue() {
        return flowValue;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        FileParser parser = new FileParser("testData.txt");
        FlowNetwork flowNetwork = new FlowNetwork(parser);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, parser.getSource(), parser.getSink());
        System.out.println("Max Flow determined: " + fordFulkerson.getFlowValue());
    }
}
