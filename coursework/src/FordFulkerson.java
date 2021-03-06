import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {
    //*will hold the path, on how we reach each vertex*//
    private FlowEdge[] edgeTo;
    //*value of the flow*//
    private int flowValue;

    public FordFulkerson(FlowNetwork flowNetwork, int source, int target) {
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

            flowValue += bottleneckCapacity;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int source, int target) {
        edgeTo = new FlowEdge[flowNetwork.getNumberOfVertices()];

        //*mark an edge as true, if the edge is in the residual network*//
        //*in other words, will be true for edges which we have already been to*//
        boolean[] marked = new boolean[flowNetwork.getNumberOfVertices()];

        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        marked[source] = true;

        while (!q.isEmpty()) {
            int v = q.remove();

            for (FlowEdge edge : flowNetwork.adjacent(v)) {
                int w = edge.otherEnd(v);

                if (edge.residualCapacity(w) > 0 && !marked[w]) {
                    edgeTo[w] = edge;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }

        return marked[target];
    }

    public int getFlowValue() {
        return flowValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileParser parser = new FileParser("testData.txt");
        FlowNetwork flowNetwork = new FlowNetwork(parser);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, parser.getSource(), parser.getSink());
        System.out.println(fordFulkerson.getFlowValue());
    }
}
