/*
 * FlowEdge API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

/**
 * FlowEdge class, which will be used to represent the Edges in the data structure
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FlowEdge {
    private final int V;
    private final int W;
    private final int CAPACITY;

    //*edge flow - will change depending on the augmented path*//
    private int flow;

    /**
     * Initialize a FlowEdge
     * @param V - from vertex V
     * @param W - to vertex W
     * @param CAPACITY - the capacity this edge can hold
     */
    public FlowEdge(int V, int W, int CAPACITY) {
        try {
            this.V = V;
            this.W = W;
            this.CAPACITY = CAPACITY;
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal Arguments passed");
        }
    }

    /**
     * @return from which vertex does the edge start
     */
    public int from() {
        return this.V;
    }

    /**
     * @return to which vertex does the edge go to
     */
    public int to() {
        return this.W;
    }

    /**
     * @return this edges capacity
     */
    public int getCapacity() {
        return this.CAPACITY;
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
        if (vertex == V) {
            return this.W;
        } else if (vertex == W) {
            return this.V;
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
        if (vertex == V) {
            return flow;
        }
        //*forward edge, vertex = w means V->W*//
        else if (vertex == W) {
            return CAPACITY - flow;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    /**
     * Add flow to this edge
     * @param vertex - if backward vertex, remove flow, else increment
     * @param delta - flow amount
     */
    public void addResidualFlow(int vertex, int delta) {
        //*backward edge*//
        if (vertex == V) {
            flow -= delta;
        }
        //*forward edge*//
        else if (vertex == W) {
            flow += delta;
        } throw new IllegalArgumentException("Illegal Vertex");
    }
}
