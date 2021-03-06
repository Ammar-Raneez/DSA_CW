/*
 * FileParser API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * FileParser class, which will be used to read in the input and parse it into a way that the FlowNetwork can accept
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FileParser {
    private final Scanner SCANNER;
    private int source;
    private int sink;
    //*store a list of array lists, where each will hold specific edge data, such that it can be provided to the graph*//
    private List<ArrayList<Integer>> edgeData;
    private int edges;

    /**
     * Constructor to initialize the fields
     * @param file - which file to read
     * @throws FileNotFoundException - thrown if specified file cannot be found
     */
    public FileParser(String file) throws FileNotFoundException {
        FileReader FILE_READER = new FileReader(file);
        this.SCANNER = new Scanner(FILE_READER);
        this.edgeData = new ArrayList<>();
        this.setSink();
        this.setSource();
        this.parseAndSetEdges();
    }

    /**
     * @return the list of edge data
     */
    public List<ArrayList<Integer>> getEdgeData() {
        return this.edgeData;
    }

    /**
     * @return total edges in the specified file
     */
    public int getEdges() {
        return this.edges;
    }

    /**
     * @return source vertex of file
     */
    public int getSource() {
        return this.source;
    }

    /**
     * @return sink (target) vertex of file
     */
    public int getSink() {
        return this.sink;
    }

    /**
     * Will handle the parsing of data
     * Loop through all lines, first value = v, second value = w, third value = edge capacity
     */
    private void parseAndSetEdges() {
        while (this.SCANNER.hasNext()) {
            String[] eachLine = this.SCANNER.nextLine().split(" ");
            int v = Integer.parseInt(eachLine[0]);
            int w = Integer.parseInt(eachLine[1]);
            int capacity = Integer.parseInt(eachLine[2]);
            this.edgeData.add(new ArrayList<>(Arrays.asList(v, w, capacity)));
            this.edges++;
        }
    }

    /**
     * source is always 0
     */
    private void setSource() {
        this.source = 0;
    }

    /**
     * First line has the sink value
     */
    private void setSink() {
        this.sink = Integer.parseInt(this.SCANNER.nextLine());
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileParser parser = new FileParser("testData.txt");
        System.out.println(parser.getEdgeData());
        System.out.println(parser.getEdges() + " " + parser.getSink() + " " + parser.getSource());
    }
}
