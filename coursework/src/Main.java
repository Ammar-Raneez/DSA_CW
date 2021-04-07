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
        FileParser parser;
        try {
            parser = new FileParser("testFiles/example.txt");
        } catch (FileNotFoundException fe) {
            // file was not there
            System.out.println("[ERROR] --> File could not be found!");
            return;
        } catch (Exception e) {
            // problem with the file format
            e.printStackTrace();
            System.out.println("[ERROR] --> stopping program...");
            return;
        }

        System.out.println(parser.getEdgesTotal());

        // create only flow networks with valid edge and vertex totals (must be non negative)
        FlowNetwork flowNetwork;
        try {
            flowNetwork = new FlowNetwork(parser);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] --> stopping program...");
            return;
        }

        // Performance Analysis
        double startTime = System.currentTimeMillis() / 1000.0;

        // only proper flow networks would work
        EdmondsCarp edmondsCarp;
        try {
            edmondsCarp = new EdmondsCarp(flowNetwork, parser.getSource(), parser.getSink());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] --> stopping program...");
            return;
        }

        double endTime = System.currentTimeMillis() / 1000.0;
        System.out.println("Max Flow determined: " + edmondsCarp.getFlowValue());
        System.out.println("Time Taken: " + (endTime - startTime));
    }
}
