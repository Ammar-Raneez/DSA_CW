/* *****************************************************************
 * Name: Ammar Raneez | 2019163 | W1761196
 * Description: FlowNetwork API, used to represent the network
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
 * FlowNetwork API, which will be used to represent the Graph Network
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
     * @throws IllegalArgumentException if vertex total is negative
     */
    public FlowNetwork(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("[ERROR] --> Negative Vertices not allowed");
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
     * @throws IllegalArgumentException - if edges or vertices value are less than 0
     */
    public FlowNetwork(FileParser parser) {
        // call previous constructor to initialize
        this(parser.getVerticesTotal());

        // Number of edges & vertices are defined in the data file itself, as specified
        this.edges = parser.getEdgesTotal();
        this.vertices = parser.getVerticesTotal();

        if (this.edges < 0 || this.vertices < 0) {
            throw new IllegalArgumentException("[ERROR] --> Negative edges or vertices not allowed");
        }

        // Add all the read edges into the Network
        for (int i = 0; i < this.edges; i++) {
            int vertexFrom = parser.getEdgeData().get(i).get(0);
            int vertexTo = parser.getEdgeData().get(i).get(1);
            int capacity = parser.getEdgeData().get(i).get(2);
            addEdge(new FlowEdge(vertexFrom, vertexTo, capacity));
        }
    }

    /**
     * Add an edge to the network
     * @param edge - edge to be added
     * @throws IllegalArgumentException if vertex is out of bounds of 0 and V-1
     */
    public void addEdge(FlowEdge edge) {
        // Get the edges forward and backward vertex
        int vertexFrom = edge.from();
        int vertexTo = edge.to();

        // must be in between 0 and V
        validVertex(vertexFrom);
        validVertex(vertexTo);

        // at the obtained vertices, add the edge, into their adjacency lists
        ADJACENCY_LIST.get(vertexFrom).add(edge);
        ADJACENCY_LIST.get(vertexTo).add(edge);
    }

    /**
     * Delete an edge from the network
     * @param edge - edge to delete
     * @throws IllegalArgumentException if vertex is out of bounds of 0 and V-1
     */
    public void deleteEdge(FlowEdge edge) {
        int vertexFrom = edge.from();
        int vertexTo = edge.to();

        // must be in between 0 and V
        validVertex(vertexFrom);
        validVertex(vertexTo);

        // removing v from w's adjacency list, and vice versa
        ADJACENCY_LIST.get(vertexFrom).remove(edge);
        ADJACENCY_LIST.get(vertexTo).remove(edge);

        // total number of edges decreases by 1 upon edge deletion
        this.edges--;
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
            int vertexFrom = q.remove();

            for (FlowEdge edge : getAdjacent(vertexFrom)) {
                int vertexTo = edge.otherEnd(vertexFrom);
                // add each unvisited neighbor to queue, and mark them as visited, whilst storing their edge in the edgeTo path
                if (edge.residualCapacity(vertexTo) > 0 && !marked[vertexTo]) {
                    edgeTo[vertexTo] = edge;
                    marked[vertexTo] = true;
                    q.add(vertexTo);
                }
            }
        }

        // if there is an augmenting path
        return marked[target];
    }

    /**
     * Get the edges incident to passed v
     * @param vertex - whose list to get
     * @return - an Array list of type FlowEdge (the list of vertices)
     * @throws IllegalArgumentException if vertex is out of bounds of 0 and V-1
     */
    public ArrayList<FlowEdge> getAdjacent(int vertex) {
        validVertex(vertex);
        return ADJACENCY_LIST.get(vertex);
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
     * @param vertex - which vertex to validate
     * @throws IllegalArgumentException if vertex is out of bounds of 0 and V-1
     * starts from 0, therefore will go till total-1
     */
    private void validVertex(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException("[ERROR] --> Illegal vertex choice: It must be less than " + vertices + " and greater than 0");
        }
    }
}
