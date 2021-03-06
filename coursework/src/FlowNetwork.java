/*
 * FlowNetwork API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * FlowNetwork class, which will be used to represent the Graph Network
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FlowNetwork {
    //*number of vertices in the Network*//
    private int V;
    //*Adjacency List implementation, will hold the vertices, and each vertex will hold the vertices they have an edge to*//
    private List<ArrayList<FlowEdge>> adjacencyList;
    //*number of edges*//
    private int E;

    /**
     * Initialize an empty flow network with V vertices and 0 edges
     * @param V - number of vertices in the network
     */
    public FlowNetwork(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Negative Vertices not allowed");
        }
        this.V = V;
        adjacencyList = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    /**
     * Initialize flow network based on an input file
     * @param parser - object of FileParser
     */
    public FlowNetwork(FileParser parser) {
        this.E = parser.getEdges();
        for (int i = 0; i < E; i++) {
            int v = parser.getEdgeData().get(i).get(0);
            int w = parser.getEdgeData().get(i).get(1);
            int capacity = parser.getEdgeData().get(i).get(2);
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    /**
     * Add an edge to the network
     * @param edge - add to be added
     */
    public void addEdge(FlowEdge edge) {
        //*Get the edges forward and backward vertex*//
        int v = edge.from();
        int w = edge.to();
        //*must be in between 0 and V*//
        validVertex(v);
        validVertex(w);
        //*at the obtained vertices, add the edge, in the adjacency list*//
        adjacencyList.get(v).add(edge);
        adjacencyList.get(w).add(edge);
        E++;
    }

    /**
     * @param v - whose list to get
     * @return - iterator (the list of vertices)
     */
    public Iterable<FlowEdge> adjacent(int v) {
        validVertex(v);
        return adjacencyList.get(v);
    }

    /**
     * @return number of vertices
     */
    public int V() {
        return this.V;
    }

    /**
     * @return number of edges
     */
    public int E() {
        return this.E;
    }

    /**
     * vertex validation
     * @param v - which vertex to validate
     */
    private void validVertex(int v) {
        if (v < 0 || v > V) {
            throw new IllegalArgumentException("Illegal vertex choice: It must be less than " + V + " and greater than 0");
        }
    }
}
