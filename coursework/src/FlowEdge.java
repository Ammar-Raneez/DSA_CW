/*
 * FlowEdge API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

/*
 * File Information
 * first line -> #vertices
 * first line -1 -> sink
 * 0 -> source
 * second line onwards -> edges
 * *each edge line -> vertex from, vertex to, capacity
 */

/**
 * FlowEdge class, which will be used to represent the Edges in the data structure
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FlowEdge {
    private final int v; //*Backward (Start Vertex, Pointing from vertex)*//
    private final int w; //*Forward (End Vertex, Pointing towards vertex)*//
    private final int capacity;

    //*edge flow - will change depending on the augmented path*//
    private int flow;

    /**
     * Initialize a FlowEdge
     * @param v - from vertex v
     * @param w - to vertex w
     * @param capacity - the capacity this edge can hold
     */
    public FlowEdge(int v, int w, int capacity) {
        try {
            this.v = v;
            this.w = w;
            this.capacity = capacity;
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal Arguments passed");
        }
    }

    /**
     * @return from which vertex does the edge start
     */
    public int from() {
        return this.v;
    }

    /**
     * @return to which vertex does the edge go to
     */
    public int to() {
        return this.w;
    }

    /**
     * @return this edges capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @return this edges flow
     */
    public int getFlow() {
        return this.flow;
    }

    /**
     * @param vertex - vertex passed to get other end
     * @return other end of passed vertex in this edge
     */
    public int otherEnd(int vertex) {
        if (vertex == v) {
            return this.w;
        } else if (vertex == w) {
            return this.v;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    //NOTE - In Residual Networks
    //Forward = capacity - flow
    //Backward = flow

    /**
     * @param vertex - passed
     * @return residual capacity
     */
    public int residualCapacity(int vertex) {
        //*backward edge*//
        if (vertex == v) {
            return flow;
        }
        //*forward edge, vertex = w means V->W*//
        else if (vertex == w) {
            return capacity - flow;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    /**
     * Add flow to this edge
     * @param vertex - if backward vertex, remove flow, else increment
     * @param delta - flow amount
     */
    public void addResidualFlow(int vertex, int delta) {
        if (!(delta >= 0)) {
            throw new IllegalArgumentException("Illegal Delta, please use Non-negative integers");
        }
        //*backward edge*//
        if (vertex == v) {
            flow -= delta;
        }
        //*forward edge*//
        else if (vertex == w) {
            flow += delta;
        }
        else {
            throw new IllegalArgumentException("Illegal Vertex");
        }

        if (!(flow >= 0)) {
            throw new IllegalArgumentException("Flow must be positive");
        }
        if (!(flow <= capacity)) {
            throw new IllegalArgumentException("Flow must not exceed allowed capacity");
        }
    }

    @Override
    public String toString() {
        return v + "->" + w + " | Flow: " + flow + " | Capacity: " + capacity;
    }
}
