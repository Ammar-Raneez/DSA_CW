/* *****************************************************************
 * Name: Ammar Raneez
 * Description: FlowNetwork API, be used to represent the network
                and handle operations associated with it
 * Written: march 2021
 * Last Updated: April 2021
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 *******************************************************************/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * FlowNetwork class, which will be used to represent the Graph Network
 * Each edge of this network is a FlowEdge which has a flow and capacity
 * Supports addition of edges, deletion of edges, and breadth-first-search to iterate over the edges
 * It further provides methods to get the number of vertices and edges and all adjacent vertices of a specific vertex
 * Uses the adjacency list representation, where it'll hold lists of FlowEdges for each vertex
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FlowNetwork {
    private int vertices;                                   // number of vertices in the Network
    // Adjacency List implementation, will hold the vertices, and each vertex will hold the edges that are incident to it
    private final List<ArrayList<FlowEdge>> ADJACENCY_LIST;
    private int edges;                                      // number of edges in the Network

    /**
     * Initialize an empty flow network with V vertices and 0 edges
     * @param V - number of vertices in the network
     */
    public FlowNetwork(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Negative Vertices not allowed");
        }
        this.vertices = V;
        ADJACENCY_LIST = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            ADJACENCY_LIST.add(new ArrayList<>());
        }
    }

    /**
     * Initialize flow network based on an input file
     * Since the FileParser is tailored specifically for an expected format
     * The values are directly obtained from the parser
     * @param parser - object of FileParser
     */
    public FlowNetwork(FileParser parser) {
        // call previous constructor to initialize
        this(parser.getVerticesTotal());

        // Number of edges & vertices are defined in the data file itself, as specified
        this.edges = parser.getEdgesTotal();
        this.vertices = parser.getVerticesTotal();

        // Add all the read edges into the Network
        for (int i = 0; i < this.edges; i++) {
            int v = parser.getEdgeData().get(i).get(0);
            int w = parser.getEdgeData().get(i).get(1);
            int capacity = parser.getEdgeData().get(i).get(2);
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    /**
     * Add an edge to the network
     * @param edge - edge to be added
     * throws IllegalArgumentException if vertex is out of bounds of 0 and V
     */
    public void addEdge(FlowEdge edge) {
        // Get the edges forward and backward vertex
        int v = edge.from();
        int w = edge.to();

        // must be in between 0 and V
        validVertex(v);
        validVertex(w);

        // at the obtained vertices, add the edge, into their adjacency lists
        ADJACENCY_LIST.get(v).add(edge);
        ADJACENCY_LIST.get(w).add(edge);
    }

    /**
     * Delete an edge from the network
     * @param edge - edge to delete
     * throws IllegalArgumentException if vertex is out of bounds of 0 and V
     */
    public void deleteEdge(FlowEdge edge) {
        int v = edge.from();
        int w = edge.to();

        // must be in between 0 and V
        validVertex(v);
        validVertex(w);

        // removing v from w's adjacency list, and vice versa
        ADJACENCY_LIST.get(v).remove(edge);
        ADJACENCY_LIST.get(w).remove(edge);
    }

    /**
     * Implements the Breadth First Search Algorithm, which will be used to find the Augmenting Path
     * @param source - network source
     * @param edgeTo - vertex path
     * @param target - network sink
     * @return - whether there is another path or not
     */
    public boolean breadthFirstSearch(int source, FlowEdge[] edgeTo, int target) {
        // mark an edge as true, if the edge is in the residual network
        // in other words, will be true for edges which we have already been to
        boolean[] marked = new boolean[getNumberOfVertices()];

         /*---------------------------------------------------------------------
         * Put source onto queue, mark source as visited
         * Repeat until Queue is Empty:
         *      Remove vertex v from queue, least recently added one
         *      Add to queue all unmarked vertices adjacent to v and mark them
         * -------------------------------------------------------------------*/
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        marked[source] = true;

        while (!q.isEmpty()) {
            int v = q.remove();

            for (FlowEdge edge : getAdjacent(v)) {
                int w = edge.otherEnd(v);
                // add each unvisited neighbor to queue, and mark them as visited, whilst storing their edge in the edgeTo path
                if (edge.residualCapacity(w) > 0 && !marked[w]) {
                    edgeTo[w] = edge;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }

        return marked[target];
    }

    /**
     * @param v - whose list to get
     * @return - an Array list of type FlowEdge (the list of vertices)
     * throws IllegalArgumentException if vertex is out of bounds of 0 and V
     */
    public ArrayList<FlowEdge> getAdjacent(int v) {
        validVertex(v);
        return ADJACENCY_LIST.get(v);
    }

    /**
     * @return number of vertices
     */
    public int getNumberOfVertices() {
        return this.vertices;
    }

    /**
     * @return number of edges
     */
    public int getNumberOfEdges() {
        return this.edges;
    }

    /**
     * vertex validation
     * @param v - which vertex to validate
     * throws IllegalArgumentException if vertex is out of bounds of 0 and V
     */
    private void validVertex(int v) {
        if (v < 0 || v > vertices) {
            throw new IllegalArgumentException("Illegal vertex choice: It must be less than " + vertices + " and greater than 0");
        }
    }
}
