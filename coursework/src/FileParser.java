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


/*------------------------------------------------------------
 * File Information
 * first line -> #vertices
 * first line -1 -> sink
 * 0 -> source
 * second line onwards -> edges
 * each edge line -> vertex from, vertex to, capacity
 *-------------------------------------------------------------*/

/**
 * FileParser class, which will be used to read in the input and parse it into a way that the FlowNetwork can accept
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FileParser {
    private final Scanner SCANNER;
    //*source of Network*//
    private int source;
    //*target of Network*//
    private int sink;
    //*store a list of array lists, where each will hold specific edge data, such that it can be provided to the graph*//
    private List<ArrayList<Integer>> edgeData;
    //*Number of edges*//
    private int edges;
    //*Number of vertices*//
    private int vertices;

    /**
     * Constructor to initialize and determine
     * @param file - which file to read
     * @throws FileNotFoundException - thrown if specified file cannot be found
     */
    public FileParser(String file) throws FileNotFoundException {
        FileReader FILE_READER = new FileReader(file);
        this.SCANNER = new Scanner(FILE_READER);
        this.edgeData = new ArrayList<>();
        this.setVertexAndSink();
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
    public int getEdgesTotal() {
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
     * @return total number of vertices
     */
    public int getVerticesTotal() {
        return this.vertices;
    }

    /**
     * Will handle the parsing of data
     * Loop and split at " ", due to each item in a line being separated by " "
     * Parse the data into an Integer before assignment, since files hold String data
     */
    private void parseAndSetEdges() {
        while (this.SCANNER.hasNext()) {
            String[] eachLine = this.SCANNER.nextLine().split(" ");
            int v = Integer.parseInt(eachLine[0]);
            int w = Integer.parseInt(eachLine[1]);
            int capacity = Integer.parseInt(eachLine[2]);
            //*store each edge data separately at index v*//
            this.edgeData.add(new ArrayList<>(Arrays.asList(v, w, capacity)));
            this.edges++;
        }
    }

    /**
     * Set source. Source is always 0
     */
    private void setSource() {
        this.source = 0;
    }

    /**
     * Set vertex count and sink, First line has the vertex count. Sink = vertices - 1
     */
    private void setVertexAndSink() {
        this.vertices = Integer.parseInt(this.SCANNER.nextLine());
        this.sink = this.vertices - 1;
    }

    @Override
    public String toString() {
        return "Edges: " + getEdgesTotal() + " " + " Vertices: " + getVerticesTotal() + " Sink: " + getSink()
                + " Source: " + getSource();
    }

    /**
     * Class Tester
     * @param args - command line arguments
     * @throws FileNotFoundException - thrown upon invalid file path
     */
    public static void main(String[] args) throws FileNotFoundException {
        FileParser parser = new FileParser("testData.txt");
        System.out.println(parser.getEdgeData());
        System.out.println(parser);
    }
}
