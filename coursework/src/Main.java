import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        FileParser parser = new FileParser("testData.txt");
        FlowNetwork flowNetwork = new FlowNetwork(parser);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, parser.getSource(), parser.getSink());
        System.out.println("Max Flow determined: " + fordFulkerson.getFlowValue());
    }
}
