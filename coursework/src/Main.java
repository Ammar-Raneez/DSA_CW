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
    public static void main(String[] args) throws InterruptedException {
        FileParser parser = null;
        try {
            parser = new FileParser("test files/example.txt");
        } catch (FileNotFoundException fe) {
            System.out.println("[ERROR] --> File could not be found!");
        }

        if (parser != null) {
            System.out.println(parser.getEdgesTotal());
//            FlowNetwork flowNetwork = new FlowNetwork(parser);
//            EdmondsCarp edmondsCarp = new EdmondsCarp(flowNetwork, parser.getSource(), parser.getSink());
//            System.out.println("Max Flow determined: " + edmondsCarp.getFlowValue());
        }
    }
}
