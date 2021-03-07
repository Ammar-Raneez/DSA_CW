/*
 * EdmondsCarp API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

/*------------------------------------------------------------------------------------------
 * Edmonds Carp is an Algorithm that implements Ford Fulkerson, using Breadth First Search
 *-----------------------------------------------------------------------------------------*/

/**
 * EdmondsCarp class, which will be used to implement the FordFulkerson Algorithm
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class EdmondsCarp {
    //*will hold the path, on how we reach each vertex*//
    private FlowEdge[] edgeTo;
    //*value of the max flow*//
    private int flowValue;

    /**
     * Initialize
     * @param flowNetwork - the FlowNetwork to perform on
     * @param source - networks source
     * @param target - networks target
     * @throws InterruptedException - thrown on Thread sleep() of printDetails()
     */
    public EdmondsCarp(FlowNetwork flowNetwork, int source, int target) throws InterruptedException {
        //*start by initializing flow to 0*//
        this.flowValue = 0;
        int augmentingPaths = 0;

        System.out.println("Flow Network: Edges = "  + flowNetwork.getNumberOfEdges() + ", Vertices = " + flowNetwork.getNumberOfVertices());

        //*Ford Fulkerson Algorithm*//
        while (hasAugmentingPath(flowNetwork, source, target)) {
            //*the maximum flow that can be pushed at a time, initialized to infinity, if it was initialized as 0*//
            //*always and only zero will be flown*//
            int bottleneckCapacity = Integer.MAX_VALUE;

            //*TODO Comment n understand*//
            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                bottleneckCapacity = Math.min(bottleneckCapacity, edgeTo[v].residualCapacity(v));
            }

            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                edgeTo[v].addResidualFlow(v, bottleneckCapacity);
            }

            augmentingPaths++;
            printDetails(bottleneckCapacity, augmentingPaths);
        }
    }

    /**
     * Print some details about each iteration
     * @param bottleneckCapacity - amount of flow for an iteration
     * @param augmentingPaths - current number of augmenting path
     * @throws InterruptedException - thrown in Thread sleep()
     */
    private void printDetails(int bottleneckCapacity, int augmentingPaths) throws InterruptedException {
        System.out.println("Bottleneck Capacity: " + bottleneckCapacity);
        System.out.print("Flow Value Incrementing from: " + flowValue + " to: ");
        flowValue += bottleneckCapacity;
        System.out.println(flowValue);
        System.out.println("Augmenting Path: " + augmentingPaths);
        Thread.sleep(1000);
        System.out.println();
    }

    /**
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
