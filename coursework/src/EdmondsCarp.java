/* *****************************************************************
 * Name: Ammar Raneez | 2019163 | W1761196
 * Description: EdmondsCarp API, will compute required max flow
                It is an algorithm that implements the Ford Fulkerson
                method using Breadth First Search
 * Written: March 2021
 * Last Updated: April 2021
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 *******************************************************************/

/**
 * EdmondsCarp API, which will be used to implement the FordFulkerson Algorithm
 * This will be used to compute the max flow of the FlowNetwork
 * Has methods to check for remaining augmenting paths and returning the max flow of the flowNetwork
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class EdmondsCarp {
    private FlowEdge[] edgeTo;          // will hold the path, on how we reach each vertex
    private int flowValue;              // value of the max flow

    /**
     * Initialize and perform the operations in computing the max flow of the passed flowNetwork
     * @param flowNetwork - the FlowNetwork to perform on
     * @param source - networks source
     * @param target - networks target
     */
    public EdmondsCarp(FlowNetwork flowNetwork, int source, int target) {
        this.flowValue = 0;         // start by initializing flow to 0
        int augmentingPaths = 0;    // augmenting paths are 0 at first

        System.out.println("Flow Network: Edges = "  + flowNetwork.getNumberOfEdges() + ", Vertices = " + flowNetwork.getNumberOfVertices() + "\n\n");

        // Ford Fulkerson Algorithm
        while (hasAugmentingPath(flowNetwork, source, target)) {
            // the maximum flow that can be pushed at a time, initialized to infinity, if it was initialized as 0
            // always and only zero will be flown
            int bottleneckCapacity = Integer.MAX_VALUE;

            // The for loops are designed in a way to "re track" the path taken, in other words, it goes backwards

            // determine bottleneck capacity, to increment the flow value
            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                bottleneckCapacity = Math.min(bottleneckCapacity, edgeTo[v].residualCapacity(v));
            }

            // add bottleneck capacity to the edges that were involved, to signify the change in flow of the edge
            // the max forward edge can hold is it's capacity, and a backward edge can hold a minimum of 0 aka empty
            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                System.out.print("Before Adding Residual Flow: " + edgeTo[v] + " || ");
                edgeTo[v].addResidualFlow(v, bottleneckCapacity);
                System.out.println("After Adding Residual Flow: " + edgeTo[v]);
            }

            // an augmenting path was created
            augmentingPaths++;
            printDetails(bottleneckCapacity, augmentingPaths);
        }
    }

    /**
     * Print some details about each iteration
     * @param bottleneckCapacity - amount of flow for an iteration
     * @param augmentingPaths - current number of augmenting path
     */
    private void printDetails(int bottleneckCapacity, int augmentingPaths) {
        System.out.println("Augmenting Path: " + augmentingPaths);
        System.out.println("Bottleneck Capacity: " + bottleneckCapacity);
        System.out.print("Flow Value Incrementing from: " + flowValue + " to: ");
        flowValue += bottleneckCapacity;
        System.out.println(flowValue);
        System.out.println();
    }

    /**
     * Checks to see where there are more augmenting paths
     * @param flowNetwork - which flow network
     * @param source - network source
     * @param target - network target
     * @return whether there's an augmenting path
     */
    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int source, int target) {
        edgeTo = new FlowEdge[flowNetwork.getNumberOfVertices()];
        return flowNetwork.breadthFirstSearch(source, edgeTo, target);
    }

    /**
     * @return graphs max flow
     */
    public int getFlowValue() {
        return flowValue;
    }
}
