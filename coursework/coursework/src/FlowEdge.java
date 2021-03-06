/* *****************************************************************
 * Name: Ammar Raneez | 2019163 | W1761196
 * Description: FlowEdge API, will handle operations of FlowEdge
                and will also be used to represent a FlowEdge
 * Written: March 2021
 * Last Updated: April 2021
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 *******************************************************************/

/**
 * FlowEdge API, which will be used to represent the Edges in the data structure
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
     * @throws IllegalArgumentException when parameters are non-negative integers or arguments are invalid completely
     */
    public FlowEdge(int vertexFrom, int vertexTo, int capacity) {
        // only positive integers are allowed
        if (vertexFrom < 0 || vertexTo < 0 || capacity < 0) {
            throw new IllegalArgumentException("[ERROR] --> from, to, and capacity must be non-negative integers");
        }

        try {
            this.VERTEX_FROM = vertexFrom;
            this.VERTEX_TO = vertexTo;
            this.CAPACITY = capacity;
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] --> Illegal Arguments passed");
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
     * Gets the other end of the edge of the passed vertex
     * @param vertex - vertex passed to get other end
     * @return other end of passed vertex in this edge
     * @throws IllegalArgumentException if the specified vertex is not proper
     */
    public int otherEnd(int vertex) {
        if (vertex == VERTEX_FROM) {
            return this.VERTEX_TO;
        } else if (vertex == VERTEX_TO) {
            return this.VERTEX_FROM;
        } throw new IllegalArgumentException("[ERROR] --> Illegal Vertex, vertex specified is neither a from or to");
    }

    /**
     * Gets the residual capacity of this edge, based on whether vertex is from or to
     * @param vertex - passed
     * @return residual capacity
     * @throws IllegalArgumentException if the specified vertex is not proper
     */
    public int residualCapacity(int vertex) {
        // backward edge
        if (vertex == VERTEX_FROM) {
            return flow;
        }
        // forward edge, vertex = w means V->W
        else if (vertex == VERTEX_TO) {
            return CAPACITY - flow;
        } throw new IllegalArgumentException("[ERROR] --> Illegal Vertex, vertex specified is neither a from or to");
    }

    /**
     * Adds residual flow to this edge
     * @param vertex - if backward vertex, remove flow, else add
     * @param flowChange - flow amount
     * @throws IllegalArgumentException if flowChange is negative, flow is negative, flow is more than capacity,
     * or if vertex is invalid
     */
    public void addResidualFlow(int vertex, int flowChange) {
        if (flowChange < 0) {
            throw new IllegalArgumentException("[ERROR] --> Illegal flowChange, must be non-negative");
        }
        // backward edge
        if (vertex == VERTEX_FROM) {
            flow -= flowChange;
        }
        // forward edge
        else if (vertex == VERTEX_TO) {
            flow += flowChange;
        }
        // invalid vertex
        else {
            throw new IllegalArgumentException("[ERROR] --> Illegal Vertex, vertex specified is neither a from or to");
        }

        // flow must always be between 0 and capacity
        if (flow < 0) {
            throw new IllegalArgumentException("[ERROR] --> Flow must be positive");
        }
        if (flow > CAPACITY) {
            throw new IllegalArgumentException("[ERROR] --> Flow must not exceed allowed capacity");
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