/*
 * Main API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

import java.io.FileNotFoundException;

/**
 * Main class, will be the main Client runner
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        FileParser parser = new FileParser("testData.txt");
        FlowNetwork flowNetwork = new FlowNetwork(parser);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, parser.getSource(), parser.getSink());
        System.out.println("Max Flow determined: " + fordFulkerson.getFlowValue());
    }
}
