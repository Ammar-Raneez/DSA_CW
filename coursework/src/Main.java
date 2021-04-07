/* *****************************************************************
 * Name: Ammar Raneez | 2019163 | W1761196
 * Description: Main API, be used to run the program
 * Written: March 2021
 * Last Updated: April 2021
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 *******************************************************************/

import java.io.FileNotFoundException;

/**
 * Main class, will be the main Client runner
 * Creates a file parser from the specified text file and uses the parser to supply needed values to
 * EdmondsCarp
 * Will use EdmondsCarp to provide the max flow
 * Will also time the algorithm
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class Main {
    public static void main(String[] args) {
        FileParser parser = null;
        try {
            parser = new FileParser("test files/example.txt");
        } catch (FileNotFoundException fe) {
            System.out.println("[ERROR] --> File could not be found!");
        }

        // do no do anything if specified file is not present
        if (parser != null) {
            System.out.println(parser.getEdgesTotal());
            FlowNetwork flowNetwork = new FlowNetwork(parser);

            // Performance Analysis
            double startTime = System.currentTimeMillis() / 1000.0;
            EdmondsCarp edmondsCarp = new EdmondsCarp(flowNetwork, parser.getSource(), parser.getSink());
            double endTime = System.currentTimeMillis() / 1000.0;
            System.out.println("Max Flow determined: " + edmondsCarp.getFlowValue());
            System.out.println("Time Taken: " + (endTime - startTime));
        }
    }
}
