/*
 * FlowEdge API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

/*--------------------------------------------------------------------
 * In Residual Networks
 * Forward = capacity - flow
 * Backward = flow
 *
 * If you have an edge that are all pointed from to another
 * Add the delta flows
 * If there is a backward edge, that flow is deducted from the edge
 * This is done to maintain local equilibrium, where total incoming
 * Flow to a vertex must be equal to total outgoing
 *-----------------------------------------------------------------*/

/**
 * FlowEdge class, which will be used to represent the Edges in the data structure
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FlowEdge {
    private final int vertexFrom; //*Backward (Start Vertex, Pointing FROM vertex)*//
    private final int vertexTo;   //*Forward (End Vertex, Pointing TO wards vertex)*//
    private final int capacity;   //*this edges capacity*//

    //*edge flow - will change depending on the augmented path*//
    private int flow;

    /**
     * Initialize a FlowEdge
     * @param vertexFrom - from vertex this
     * @param vertexTo - to vertex this
     * @param capacity - the capacity this edge can hold
     */
    public FlowEdge(int vertexFrom, int vertexTo, int capacity) {
        try {
            this.vertexFrom = vertexFrom;
            this.vertexTo = vertexTo;
            this.capacity = capacity;
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal Arguments passed");
        }
    }

    /**
     * @return from which vertex does the edge start
     */
    public int from() {
        return this.vertexFrom;
    }

    /**
     * @return to which vertex does the edge go to
     */
    public int to() {
        return this.vertexTo;
    }

    /**
     * @return this edges capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @return this edges current flow
     */
    public int getFlow() {
        return this.flow;
    }

    /**
     * @param vertex - vertex passed to get other end
     * @return other end of passed vertex in this edge
     */
    public int otherEnd(int vertex) {
        if (vertex == vertexFrom) {
            return this.vertexTo;
        } else if (vertex == vertexTo) {
            return this.vertexFrom;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    /**
     * @param vertex - passed
     * @return residual capacity
     */
    public int residualCapacity(int vertex) {
        //*backward edge*//
        if (vertex == vertexFrom) {
            return flow;
        }
        //*forward edge, vertex = w means V->W*//
        else if (vertex == vertexTo) {
            return capacity - flow;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    /**
     * Add flow to this edge
     * @param vertex - if backward vertex, remove flow, else add
     * @param delta - flow amount
     */
    public void addResidualFlow(int vertex, int delta) {
        if (!(delta >= 0)) {
            throw new IllegalArgumentException("Illegal Delta, please use Non-negative integers");
        }
        //*backward edge*//
        if (vertex == vertexFrom) {
            flow -= delta;
        }
        //*forward edge*//
        else if (vertex == vertexTo) {
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
        return vertexFrom + "->" + vertexTo + " | Flow: " + flow + " | Capacity: " + capacity;
    }
}
