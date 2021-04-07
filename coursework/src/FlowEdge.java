/* *****************************************************************
 * Name: Ammar Raneez | 2019163 | W1761196
 * Description: FlowEdge API, will handle operations of FlowEdge
                and will also be used to represent a FlowEdge
 * Written: March 2021
 * Last Updated: April 2021
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 *******************************************************************/

/**
 * FlowEdge class, which will be used to represent the Edges in the data structure
 * Each edge has a vertex from and a vertex to, a capacity and a flow
 * This API provides methods for accessing the other end of a vertex, altering amount of flow
 * and determining the residual capacity
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FlowEdge {
    private final int VERTEX_FROM; // Backward (Start Vertex, Pointing FROM vertex)
    private final int VERTEX_TO;   // Forward (End Vertex, Pointing TO wards vertex)
    private final int CAPACITY;    // this edges capacity

    private int flow;              // edge flow - will change depending on the augmented path

    /**
     * Initialize a FlowEdge
     * @param vertexFrom - from vertex this
     * @param vertexTo - to vertex this
     * @param capacity - the capacity this edge can hold
     */
    public FlowEdge(int vertexFrom, int vertexTo, int capacity) {
        try {
            // only positive integers are allowed
            if (vertexFrom < 0 || vertexTo < 0 || capacity < 0) {
                throw new Exception();
            }
            this.VERTEX_FROM = vertexFrom;
            this.VERTEX_TO = vertexTo;
            this.CAPACITY = capacity;
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] - Illegal Arguments passed");
        }
    }

    /**
     * @return from which vertex does the edge start
     */
    public int from() {
        return this.VERTEX_FROM;
    }

    /**
     * @return to which vertex does the edge go to
     */
    public int to() {
        return this.VERTEX_TO;
    }

    /**
     * @return this edges capacity
     */
    public int getCapacity() {
        return this.CAPACITY;
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
        if (vertex == VERTEX_FROM) {
            return this.VERTEX_TO;
        } else if (vertex == VERTEX_TO) {
            return this.VERTEX_FROM;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    /**
     * Gets the residual capacity of this edge, based on whether vertex is from or to
     * @param vertex - passed
     * @return residual capacity
     */
    public int residualCapacity(int vertex) {
        // backward edge
        if (vertex == VERTEX_FROM) {
            return flow;
        }
        // forward edge, vertex = w means V->W
        else if (vertex == VERTEX_TO) {
            return CAPACITY - flow;
        } throw new IllegalArgumentException("Illegal Vertex");
    }

    /**
     * Adds residual flow to this edge
     * @param vertex - if backward vertex, remove flow, else add
     * @param delta - flow amount
     * throws IllegalArgumentException if delta is negative, flow is negative, flow is more than capacity, and if vertex
     * is invalid
     */
    public void addResidualFlow(int vertex, int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Illegal Delta, please use Non-negative integers");
        }
        // backward edge
        if (vertex == VERTEX_FROM) {
            flow -= delta;
        }
        // forward edge
        else if (vertex == VERTEX_TO) {
            flow += delta;
        }
        // invalid vertex
        else {
            throw new IllegalArgumentException("Illegal Vertex");
        }

        // flow must always be between 0 and capacity
        if (flow < 0) {
            throw new IllegalArgumentException("Flow must be positive");
        }
        if (flow > CAPACITY) {
            throw new IllegalArgumentException("Flow must not exceed allowed capacity");
        }
    }

    /**
     * Overrun toString()
     * @return - what to display when printing a FlowEdge
     */
    @Override
    public String toString() {
        return "Flow Edge: " + VERTEX_FROM + "->" + VERTEX_TO + ", Flow: " + flow + ", Capacity: " + CAPACITY;
    }
}

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