/* *****************************************************************
 * Name: Ammar Raneez | 2019163 | W1761196
 * Description: FileParser API, be used to parse the text files
 * Written: March 2021
 * Last Updated: April 2021
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 *******************************************************************/

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * FileParser API, which will be used to read in the input and parse it into a way that the FlowNetwork can accept
 * Provides method to get the list of edge data, which can be used to create a FlowNetwork
 * Also Provides methods to get and set the source, sink and vertices. And to get total number of vertices and edges
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FileParser {
    private final Scanner SCANNER;

    private int source;         // source of Network
    private int sink;           // target of Network
    // store a list of array lists, where each will hold specific edge data, such that it can be provided to the graph
    private List<ArrayList<Integer>> edgeData;
    private int edges;          // Number of edges
    private int vertices;       // Number of vertices

    /**
     * Constructor to initialize and determine
     * @param file - which file to read
     * @throws Exception - thrown if specified file cannot be found, or file not in expected format
     */
    public FileParser(String file) throws Exception {
        FileReader FILE_READER = new FileReader(file);
        this.SCANNER = new Scanner(FILE_READER);
        this.edgeData = new ArrayList<>();
        this.setVertex();
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
     * If this format is not provided, an exception is thrown
     * @throws Exception if expected line format is not provided
     */
    private void parseAndSetEdges() throws Exception {
        while (this.SCANNER.hasNext()) {
            try {
                // perform trimming, so that any extra whitespaces on both ends are removed, and to avoid empty lines
                String[] eachLine = this.SCANNER.nextLine().trim().split(" ");
                int v = Integer.parseInt(eachLine[0]);
                int w = Integer.parseInt(eachLine[1]);
                int capacity = Integer.parseInt(eachLine[2]);
                // store each edge data separately at index v
                this.edgeData.add(new ArrayList<>(Arrays.asList(v, w, capacity)));
                this.edges++;
            } catch (Exception e) {
                throw new Exception("[ERROR] --> this line is not in the expected format of 3 space separated integers");
            }
        }
    }

    /**
     * Set source. Source is always 0
     */
    private void setSource() {
        this.source = 0;
    }

    /**
     * Set Sink. Sink = #Vertices - 1
     */
    private void setSink() {
        this.sink = this.vertices - 1;
    }

    /**
     * Set vertex count. First line has the vertex count
     * Expects a single string that can be converted to integer
     * If not, throws an exception
     * @throws Exception if there is an empty file, or first line format not right, as mentioned
     */
    private void setVertex() throws Exception {
        try {
            this.vertices = Integer.parseInt(this.SCANNER.nextLine().trim());
        } catch (Exception e) {
           throw new Exception("[ERROR] --> file is empty OR the first line is not of right format");
        }
    }

    /**
     * Overrun toString()
     * @return what to display when printing the FileParser
     */
    @Override
    public String toString() {
        return "Edges: " + getEdgesTotal() + " " + " Vertices: " + getVerticesTotal() + " Sink: " + getSink()
                + " Source: " + getSource();
    }
}

/*------------------------------------------------------------
 * File Information
 * first line -> #vertices
 * first line -1 -> sink
 * 0 -> source
 * second line onwards -> edges
 * each edge line -> vertex from, vertex to, capacity
 *-------------------------------------------------------------*/